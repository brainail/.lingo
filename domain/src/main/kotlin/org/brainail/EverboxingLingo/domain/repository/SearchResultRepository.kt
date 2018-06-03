package org.brainail.EverboxingLingo.domain.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import org.brainail.EverboxingLingo.domain.model.SearchResult

interface SearchResultRepository {
    fun clearSearchResults(): Completable
    fun saveSearchResults(searchResults: List<SearchResult>): Completable
    fun getSearchResults(query: String): Flowable<List<SearchResult>>
}