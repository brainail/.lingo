package org.brainail.EverboxingLingo.data.source.suggestion

import org.brainail.EverboxingLingo.data.repository.suggestion.SuggestionCache
import org.brainail.EverboxingLingo.data.repository.suggestion.SuggestionDataSource
import javax.inject.Inject

class SuggestionsDataSourceFactory @Inject constructor(
        private val suggestionCache: SuggestionCache,
        private val suggestionsCacheDataSource: SuggestionsCacheDataSource,
        private val suggestionsRemoteDataSource: SuggestionsRemoteDataSource) {

    fun obtainDataSource(): SuggestionDataSource {
        if (suggestionCache.isCached() && !suggestionCache.isExpired()) {
            return obtainCacheDataSource()
        }
        return obtainRemoteDataSource()
    }

    fun obtainCacheDataSource(): SuggestionDataSource {
        return suggestionsCacheDataSource
    }

    fun obtainRemoteDataSource(): SuggestionDataSource {
        return suggestionsRemoteDataSource
    }
}