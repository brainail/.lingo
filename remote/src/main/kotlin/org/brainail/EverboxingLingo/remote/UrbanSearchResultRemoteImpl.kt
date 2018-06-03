package org.brainail.EverboxingLingo.remote

import io.reactivex.Single
import org.brainail.EverboxingLingo.data.model.SearchResultEntity
import org.brainail.EverboxingLingo.data.repository.search.SearchResultRemote
import org.brainail.EverboxingLingo.remote.mapper.UrbanSearchResultRemoteMapper
import javax.inject.Inject

class UrbanSearchResultRemoteImpl @Inject constructor(
        private val urbanService: UrbanService,
        private val entityMapper: UrbanSearchResultRemoteMapper) : SearchResultRemote {

    override fun getSearchResults(query: String): Single<List<SearchResultEntity>> {
        TODO("No-impl")
    }
}