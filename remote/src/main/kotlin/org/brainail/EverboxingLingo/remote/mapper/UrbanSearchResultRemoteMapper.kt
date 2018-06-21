package org.brainail.EverboxingLingo.remote.mapper

import org.brainail.EverboxingLingo.data.model.SearchResultEntity
import org.brainail.EverboxingLingo.remote.model.UrbanSearchResultRemoteEntity
import javax.inject.Inject

class UrbanSearchResultRemoteMapper @Inject constructor()
    : Mapper<UrbanSearchResultRemoteEntity, SearchResultEntity> {

    override fun mapFromRemote(input: UrbanSearchResultRemoteEntity): SearchResultEntity {
        return SearchResultEntity(input.defid, input.word, input.definition, input.example, input.permalink)
    }
}