package org.brainail.EverboxingLingo.data.source.search

import org.brainail.EverboxingLingo.data.repository.search.SearchResultCache
import org.brainail.EverboxingLingo.data.repository.search.SearchResultDataSource
import javax.inject.Inject

class SearchResultDataSourceFactory @Inject constructor(
        private val searchResultCache: SearchResultCache,
        private val searchResultCacheDataSource: SearchResultCacheDataSource,
        private val searchResultRemoteDataSource: SearchResultRemoteDataSource) {

    fun obtainDataSource(): SearchResultDataSource {
        if (searchResultCache.isCached() && !searchResultCache.isExpired()) {
            return obtainCacheDataSource()
        }
        return obtainRemoteDataSource()
    }

    fun obtainCacheDataSource(): SearchResultDataSource {
        return searchResultCacheDataSource
    }

    fun obtainRemoteDataSource(): SearchResultDataSource {
        return searchResultRemoteDataSource
    }
}