package org.brainail.everboxing.lingo.data.source.search

import io.reactivex.Completable
import io.reactivex.Flowable
import org.brainail.everboxing.lingo.data.model.SearchResultEntity
import org.brainail.everboxing.lingo.data.repository.search.SearchResultCache
import org.brainail.everboxing.lingo.data.repository.search.SearchResultDataSource
import javax.inject.Inject

class SearchResultCacheDataSource @Inject constructor(
        private val searchResultCache: SearchResultCache): SearchResultDataSource {

    override fun clearSearchResults(): Completable {
        return searchResultCache.clearSearchResults()
    }

    override fun saveSearchResults(searchResults: List<SearchResultEntity>): Completable {
        return searchResultCache.saveSearchResults(searchResults)
    }

    override fun getSearchResults(query: String): Flowable<List<SearchResultEntity>> {
        return searchResultCache.getSearchResults(query)
    }
}