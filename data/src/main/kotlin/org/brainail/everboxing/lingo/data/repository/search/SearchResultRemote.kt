package org.brainail.everboxing.lingo.data.repository.search

import io.reactivex.Single
import org.brainail.everboxing.lingo.data.model.SearchResultEntity

interface SearchResultRemote {
    fun getSearchResults(query: String): Single<List<SearchResultEntity>>
}
