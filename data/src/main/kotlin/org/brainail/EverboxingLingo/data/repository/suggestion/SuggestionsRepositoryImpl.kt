package org.brainail.EverboxingLingo.data.repository.suggestion

import io.reactivex.Completable
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
                        suggestionMapper.mapFromEntity(listItem).copy(highlights = query)
                    }
                }
    }

    private fun saveSuggestionEntities(suggestions: List<SuggestionEntity>): Completable {
        return dataSourceFactory.obtainCacheDataSource().saveSuggestions(suggestions)
    }
}