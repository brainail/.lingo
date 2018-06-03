package org.brainail.EverboxingLingo.data.source.search

import io.reactivex.Completable
import io.reactivex.Flowable
import org.brainail.EverboxingLingo.data.model.SearchResultEntity
import org.brainail.EverboxingLingo.data.repository.search.SearchResultDataSource
import org.brainail.EverboxingLingo.data.repository.search.SearchResultRemote
import javax.inject.Inject

class SearchResultRemoteDataSource @Inject constructor(
        private val searchResultRemote: SearchResultRemote): SearchResultDataSource {

    override fun clearSearchResults(): Completable {
        throw UnsupportedOperationException()
    }

    override fun saveSearchResults(searchResults: List<SearchResultEntity>): Completable {
        throw UnsupportedOperationException()
    }

    override fun getSearchResults(query: String): Flowable<List<SearchResultEntity>> {
        return searchResultRemote.getSearchResults(query).toFlowable()
    }
}