package org.brainail.EverboxingLingo.data.repository.suggestion

import android.os.SystemClock
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.brainail.EverboxingLingo.data.mapper.SuggestionDataMapper
import org.brainail.EverboxingLingo.data.model.SuggestionEntity
import org.brainail.EverboxingLingo.data.source.suggestion.SuggestionsDataSourceFactory
import org.brainail.EverboxingLingo.data.source.suggestion.SuggestionsRemoteDataSource
import org.brainail.EverboxingLingo.domain.model.Suggestion
import org.brainail.EverboxingLingo.domain.repository.SuggestionsRepository
import javax.inject.Inject

class SuggestionsRepositoryImpl @Inject constructor(
        private val dataSourceFactory: SuggestionsDataSourceFactory,
        private val suggestionMapper: SuggestionDataMapper) : SuggestionsRepository {

    override fun clearSuggestions(): Completable {
        return dataSourceFactory.obtainCacheDataSource().clearSuggestions()
    }

    override fun saveSuggestions(suggestions: List<Suggestion>): Completable {
        val suggestionEntities = suggestions.map { suggestionMapper.mapToEntity(it) }
        return saveSuggestionEntities(suggestionEntities)
    }

    private fun saveSuggestionEntities(suggestions: List<SuggestionEntity>): Completable {
        return dataSourceFactory.obtainCacheDataSource().saveSuggestions(suggestions)
    }

    override fun getSuggestions(query: String): Single<List<Suggestion>> {
        val dataSource = dataSourceFactory.obtainDataSource()
        return dataSource.getSuggestions(query)
                .flatMap {
                    if (dataSource is SuggestionsRemoteDataSource) {
                        saveSuggestionEntities(it).toSingle { it }
                    } else {
                        Single.just(it)
                    }
                }
                .map { list ->
                    list.map { listItem ->
                        suggestionMapper.mapFromEntity(listItem)
                    }
                }
    }

    private fun findSuggestions(query: String): Observable<List<Suggestion>> = Observable.fromCallable {
        // https://api.urbandictionary.com/v0/autocomplete?term=holymoly
        // https//api.urbandictionary.com/v0/autocomplete-extra?term=holymoly
        // https://api.urbandictionary.com/v0/define with ?term=WORD_HERE or ?defid=DEFID_HERE
        // https://api.urbandictionary.com/v0/random
        // https://api.urbandictionary.com/v0/vote POST: {defid: 665139, direction: "up"}
        SystemClock.sleep(2000)
        if (query.startsWith("ex")) {
            throw RuntimeException()
        } else {
            listOf(Suggestion("0. " + query, "1. " + query))
        }
    }
}