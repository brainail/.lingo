package org.brainail.EverboxingLingo.data.repository.search

import io.reactivex.Single
import org.brainail.EverboxingLingo.data.model.SearchResultEntity

interface SearchResultRemote {
    fun getSearchResults(query: String): Single<List<SearchResultEntity>>
}