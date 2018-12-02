package org.brainail.everboxing.lingo.cache.mapper

import org.brainail.everboxing.lingo.cache.model.SuggestionCacheEntity
import org.brainail.everboxing.lingo.data.model.SuggestionEntity
import javax.inject.Inject

class SuggestionCacheMapper @Inject constructor()
    : Mapper<SuggestionCacheEntity, SuggestionEntity> {

    override fun mapFromCache(input: SuggestionCacheEntity): SuggestionEntity {
        return SuggestionEntity(input.word, input.description, input.isRecent, input.id)
    }

    override fun mapToCache(input: SuggestionEntity): SuggestionCacheEntity {
        return SuggestionCacheEntity(input.id, input.word, input.description, input.isRecent)
    }
}
