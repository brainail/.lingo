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
import org.brainail.everboxing.lingo.data.mapper.SuggestionDataMapper
import org.brainail.everboxing.lingo.data.model.SuggestionEntity
import org.brainail.everboxing.lingo.data.source.suggestion.SuggestionsDataSourceFactory
import org.brainail.everboxing.lingo.domain.model.Suggestion
import org.brainail.everboxing.lingo.domain.repository.SuggestionsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SuggestionsRepositoryImpl @Inject constructor(
    private val dataSourceFactory: SuggestionsDataSourceFactory,
    private val suggestionMapper: SuggestionDataMapper
) : SuggestionsRepository {

    override fun clearSuggestions(): Completable {
        return dataSourceFactory.obtainCacheDataSource().clearSuggestions()
    }

    override fun saveSuggestions(suggestions: List<Suggestion>): Completable {
        val suggestionEntities = suggestions.map { suggestionMapper.mapToEntity(it) }
        return saveSuggestionEntities(suggestionEntities)
    }

    override fun getRecentSuggestions(query: String, limit: Int): Flowable<List<Suggestion>> {
        return dataSourceFactory.obtainCacheDataSource()
            .getRecentSuggestions(query, limit)
            .map { it.map { suggestionMapper.mapFromEntity(it).copy(highlights = query) } }
    }

    override fun getSuggestions(query: String, limit: Int): Flowable<List<Suggestion>> {
        return dataSourceFactory.obtainRemoteDataSource()
            .getSuggestions(query)
            .map { it.map { suggestionMapper.mapFromEntity(it).copy(highlights = query) } }
    }

    override fun saveSuggestionAsRecent(suggestion: Suggestion): Completable {
        return saveSuggestionEntities(listOf(suggestionMapper.mapToEntity(suggestion).copy(isRecent = true)))
    }

    private fun saveSuggestionEntities(suggestions: List<SuggestionEntity>): Completable {
        return dataSourceFactory.obtainCacheDataSource().saveSuggestions(suggestions)
    }
}
