package org.brainail.EverboxingLingo.cache.mapper

import org.brainail.EverboxingLingo.cache.model.SuggestionCacheEntity
import org.brainail.EverboxingLingo.data.model.SuggestionEntity
import javax.inject.Inject

class SuggestionCacheMapper @Inject constructor()
    : Mapper<SuggestionCacheEntity, SuggestionEntity> {

    override fun mapFromCache(input: SuggestionCacheEntity): SuggestionEntity {
        TODO("No-impl")
    }

    override fun mapToCache(input: SuggestionEntity): SuggestionCacheEntity {
        TODO("No-impl")
    }
}