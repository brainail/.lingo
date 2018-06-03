package org.brainail.EverboxingLingo.data.repository.search

import io.reactivex.Completable
import io.reactivex.Flowable
import org.brainail.EverboxingLingo.data.model.SearchResultEntity

interface SearchResultCache {
    fun clearSearchResults(): Completable
    fun saveSearchResults(searchResults: List<SearchResultEntity>): Completable
    fun getSearchResults(query: String): Flowable<List<SearchResultEntity>>
    fun isCached(): Boolean
    fun isExpired(): Boolean
}