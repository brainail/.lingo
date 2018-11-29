package org.brainail.everboxing.lingo.domain.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import org.brainail.everboxing.lingo.domain.model.SearchResult

interface SearchResultRepository {
    fun clearSearchResults(): Completable
    fun saveSearchResults(searchResults: List<SearchResult>): Completable
    fun getSearchResults(query: String): Flowable<List<SearchResult>>
    fun favoriteSearchResult(id: Int): Completable
    fun forgetSearchResult(id: Int): Completable
    fun installUrbanSearchResult(pathToData: String): Completable
}