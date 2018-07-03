package org.brainail.EverboxingLingo.data.repository.suggestion

import io.reactivex.Completable
import io.reactivex.Flowable
import org.brainail.EverboxingLingo.data.mapper.SuggestionDataMapper
import org.brainail.EverboxingLingo.data.model.SuggestionEntity
import org.brainail.EverboxingLingo.data.source.suggestion.SuggestionsDataSourceFactory
import org.brainail.EverboxingLingo.domain.model.Suggestion
import org.brainail.EverboxingLingo.domain.repository.SuggestionsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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