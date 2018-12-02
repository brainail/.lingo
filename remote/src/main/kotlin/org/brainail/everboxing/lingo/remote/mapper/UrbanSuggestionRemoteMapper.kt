package org.brainail.everboxing.lingo.remote.mapper

import org.brainail.everboxing.lingo.data.model.SuggestionEntity
import org.brainail.everboxing.lingo.remote.model.UrbanSuggestionRemoteEntity
import javax.inject.Inject

class UrbanSuggestionRemoteMapper @Inject constructor()
    : Mapper<UrbanSuggestionRemoteEntity, SuggestionEntity> {

    override fun mapFromRemote(input: UrbanSuggestionRemoteEntity): SuggestionEntity {
        return SuggestionEntity(input.term, input.preview)
    }
}
