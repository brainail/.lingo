package org.brainail.EverboxingLingo.cache.mapper

import org.brainail.EverboxingLingo.cache.model.SearchResultCacheEntity
import org.brainail.EverboxingLingo.data.model.SearchResultEntity
import javax.inject.Inject

class SearchResultCacheMapper @Inject constructor()
    : Mapper<SearchResultCacheEntity, SearchResultEntity> {

    override fun mapFromCache(input: SearchResultCacheEntity): SearchResultEntity {
        TODO("No-impl")
    }

    override fun mapToCache(input: SearchResultEntity): SearchResultCacheEntity {
        TODO("No-impl")
    }
}