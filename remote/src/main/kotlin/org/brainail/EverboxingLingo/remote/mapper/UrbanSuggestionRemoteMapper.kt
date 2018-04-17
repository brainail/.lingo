package org.brainail.EverboxingLingo.remote.mapper

import org.brainail.EverboxingLingo.data.model.SuggestionEntity
import org.brainail.EverboxingLingo.remote.model.UrbanSuggestionRemoteEntity
import javax.inject.Inject

class UrbanSuggestionRemoteMapper @Inject constructor()
    : Mapper<UrbanSuggestionRemoteEntity, SuggestionEntity> {

    override fun mapFromRemote(input: UrbanSuggestionRemoteEntity): SuggestionEntity {
        return SuggestionEntity(input.term, input.preview)
    }
}