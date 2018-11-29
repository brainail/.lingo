package org.brainail.everboxing.lingo.data.repository.search

import io.reactivex.Completable
import io.reactivex.Flowable
import org.brainail.everboxing.lingo.data.model.SearchResultEntity

interface SearchResultDataSource {
    fun clearSearchResults(): Completable = throw UnsupportedOperationException()
    fun saveSearchResults(searchResults: List<SearchResultEntity>): Completable = throw UnsupportedOperationException()
    fun getSearchResults(query: String): Flowable<List<SearchResultEntity>> = throw UnsupportedOperationException()
    fun favoriteSearchResult(id: Int): Completable = throw UnsupportedOperationException()
    fun forgetSearchResult(id: Int): Completable = throw UnsupportedOperationException()
    fun installUrbanSearchResult(pathToData: String): Completable = throw UnsupportedOperationException()
}