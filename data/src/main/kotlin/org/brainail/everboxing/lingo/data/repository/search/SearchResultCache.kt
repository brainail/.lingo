package org.brainail.everboxing.lingo.data.repository.search

import io.reactivex.Completable
import io.reactivex.Flowable
import org.brainail.everboxing.lingo.data.model.SearchResultEntity

interface SearchResultCache {
    fun clearSearchResults(): Completable
    fun saveSearchResults(searchResults: List<SearchResultEntity>): Completable
    fun getSearchResults(query: String): Flowable<List<SearchResultEntity>>
    fun favoriteSearchResult(id: Int): Completable
    fun forgetSearchResult(id: Int): Completable
    fun installUrbanSearchResult(pathToData: String): Completable

    fun isCached(): Boolean = false
    fun isExpired(): Boolean = true
}