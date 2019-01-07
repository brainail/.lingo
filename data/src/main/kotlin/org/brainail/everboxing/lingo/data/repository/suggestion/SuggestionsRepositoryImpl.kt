/*
 * Copyright 2018 Malyshev Yegor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brainail.everboxing.lingo.data.repository.suggestion

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import org.brainail.everboxing.lingo.data.mapper.SuggestionDataMapper
import org.brainail.everboxing.lingo.data.model.SuggestionEntity
import org.brainail.everboxing.lingo.data.model.toSuggestion
import org.brainail.everboxing.lingo.data.source.search.SearchResultsDataSourceFactory
import org.brainail.everboxing.lingo.data.source.suggestion.SuggestionsDataSourceFactory
import org.brainail.everboxing.lingo.domain.executor.AppExecutors
import org.brainail.everboxing.lingo.domain.model.Suggestion
import org.brainail.everboxing.lingo.domain.repository.SuggestionsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SuggestionsRepositoryImpl @Inject constructor(
    private val suggestionsDataSourceFactory: SuggestionsDataSourceFactory,
    private val searchResultsDataSourceFactory: SearchResultsDataSourceFactory,
    private val suggestionMapper: SuggestionDataMapper,
    private val appExecutors: AppExecutors
) : SuggestionsRepository {

    override fun getSuggestions(query: String, limitOfRecent: Int, limitOfOthers: Int): Flowable<List<Suggestion>> {
        val remoteSuggestions = getRemoteSuggestions(query)
        val cachedSuggestions = getCachedSuggestions(query, limitOfRecent, limitOfOthers)
            .compose(appExecutors.applyFlowableBackgroundSchedulers()) // be sure to run in parallel
        val cachedSearchResultsSuggestions = when (query.isBlank()) {
            true -> Flowable.just(emptyList())
            else -> getCachedSearchResultsSuggestions(query, limitOfOthers)
                .compose(appExecutors.applyFlowableBackgroundSchedulers()) // be sure to run in parallel
        }
        return Flowable.combineLatest(
            cachedSuggestions,
            remoteSuggestions,
            cachedSearchResultsSuggestions,
            Function3 { cached, remote, cachedSearchResults ->
                mergeSuggestions(cached, remote, cachedSearchResults, limitOfOthers)
            }
        )
    }

    override fun getFavoriteSuggestions(
        query: String,
        limitOfRecent: Int,
        limitOfOthers: Int
    ): Flowable<List<Suggestion>> {
        val recentSuggestions = suggestionsDataSourceFactory.obtainCacheDataSource()
            .getRecentSuggestions(query, limitOfRecent)
            .map { it.map { suggestion -> suggestionMapper.mapF(suggestion) } }
        val cachedFavoriteSearchResultsSuggestions = when (query.isBlank()) {
            true -> Flowable.just(emptyList())
            else -> getFavoriteCachedSearchResultsSuggestions(query, limitOfOthers)
                .compose(appExecutors.applyFlowableBackgroundSchedulers()) // be sure to run in parallel
        }
        return Flowable.combineLatest(
            recentSuggestions,
            cachedFavoriteSearchResultsSuggestions,
            BiFunction { recent, cachedFavoriteSearchResults ->
                recent + cachedFavoriteSearchResults
            }
        )
    }

    override fun getHistorySuggestions(
        query: String,
        limitOfRecent: Int,
        limitOfOthers: Int
    ): Flowable<List<Suggestion>> {
        val recentSuggestions = suggestionsDataSourceFactory.obtainCacheDataSource()
            .getRecentSuggestions(query, limitOfRecent)
            .map { it.map { suggestion -> suggestionMapper.mapF(suggestion) } }
        val cachedHistorySearchResultsSuggestions = when (query.isBlank()) {
            true -> Flowable.just(emptyList())
            else -> getHistoryCachedSearchResultsSuggestions(query, limitOfOthers)
                .compose(appExecutors.applyFlowableBackgroundSchedulers()) // be sure to run in parallel
        }
        return Flowable.combineLatest(
            recentSuggestions,
            cachedHistorySearchResultsSuggestions,
            BiFunction { recent, cachedHistorySearchResults ->
                recent + cachedHistorySearchResults
            }
        )
    }

    override fun saveSuggestion(suggestion: Suggestion): Completable {
        return saveSuggestionEntities(listOf(suggestionMapper.mapT(suggestion)))
    }

    private fun getCachedSuggestions(
        query: String,
        limitOfRecent: Int,
        limitOfOthers: Int
    ): Flowable<List<Suggestion>> {
        val cachedSource = suggestionsDataSourceFactory.obtainCacheDataSource()
        return Flowable.combineLatest(
            cachedSource.getRecentSuggestions(query, limitOfRecent),
            cachedSource.getNonRecentSuggestions(query, limitOfOthers),
            BiFunction { recent, others ->
                (recent + others).map { suggestion -> suggestionMapper.mapF(suggestion) }
            }
        )
    }

    private fun getRemoteSuggestions(query: String): Flowable<List<Suggestion>> {
        return suggestionsDataSourceFactory.obtainRemoteDataSource()
            .getSuggestions(query)
            .onErrorReturn { emptyList() }
            .map { it.map { suggestion -> suggestionMapper.mapF(suggestion) } }
    }

    private fun getCachedSearchResultsSuggestions(query: String, limitOfOthers: Int): Flowable<List<Suggestion>> {
        return searchResultsDataSourceFactory.obtainCacheDataSource()
            .getDistinctByWordSearchResults(query, limitOfOthers)
            .map { it.map { searchResult -> suggestionMapper.mapF(searchResult.toSuggestion()) } }
    }

    private fun getFavoriteCachedSearchResultsSuggestions(
        query: String,
        limitOfOthers: Int
    ): Flowable<List<Suggestion>> {
        return searchResultsDataSourceFactory.obtainCacheDataSource()
            .getDistinctByWordFavoriteSearchResults(query, limitOfOthers)
            .map { it.map { searchResult -> suggestionMapper.mapF(searchResult.toSuggestion()) } }
    }

    private fun getHistoryCachedSearchResultsSuggestions(
        query: String,
        limitOfOthers: Int
    ): Flowable<List<Suggestion>> {
        return searchResultsDataSourceFactory.obtainCacheDataSource()
            .getDistinctByWordHistorySearchResults(query, limitOfOthers)
            .map { it.map { searchResult -> suggestionMapper.mapF(searchResult.toSuggestion()) } }
    }

    /**
     * Merges suggestions as (recent | fresh | cached)
     */
    private fun mergeSuggestions(
        cached: List<Suggestion>,
        remote: List<Suggestion>,
        cachedSearchResults: List<Suggestion>,
        limitOfOthers: Int
    ): List<Suggestion> {
        val (cachedRecent, cachedOthers) = cached.partition { it.isRecent }
        val remoteSet = remote.map { it.word.toLowerCase() }.toHashSet()
        val allCachedNew = (cachedOthers + cachedSearchResults)
            .distinctBy { it.word.toLowerCase() }
            .filter { it.word !in remoteSet }
        return cachedRecent + (remote + allCachedNew).take(limitOfOthers)
    }

    private fun saveSuggestionEntities(suggestions: List<SuggestionEntity>): Completable {
        return suggestionsDataSourceFactory.obtainCacheDataSource().saveSuggestions(suggestions)
    }
}
