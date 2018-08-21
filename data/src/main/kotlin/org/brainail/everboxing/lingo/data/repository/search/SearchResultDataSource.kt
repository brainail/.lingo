package org.brainail.everboxing.lingo.data.repository.search

import io.reactivex.Completable
import io.reactivex.Flowable
import org.brainail.everboxing.lingo.data.model.SearchResultEntity

interface SearchResultDataSource {
    fun clearSearchResults(): Completable
    fun saveSearchResults(searchResults: List<SearchResultEntity>): Completable
    fun getSearchResults(query: String): Flowable<List<SearchResultEntity>>
}