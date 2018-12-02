package org.brainail.everboxing.lingo.cache.mapper

import org.brainail.everboxing.lingo.cache.model.SearchResultCacheEntity
import org.brainail.everboxing.lingo.data.model.SearchResultEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchResultCacheMapper @Inject constructor()
    : Mapper<SearchResultCacheEntity, SearchResultEntity> {

    override fun mapFromCache(input: SearchResultCacheEntity): SearchResultEntity {
        return SearchResultEntity(input.definitionId,
                input.word, input.definition, input.example, input.link, input.id, input.favorite)
    }

    override fun mapToCache(input: SearchResultEntity): SearchResultCacheEntity {
        return SearchResultCacheEntity(input.id, input.definitionId,
                input.word, input.definition, input.example, input.link, input.favorite)
    }
}
