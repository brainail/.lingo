package org.brainail.everboxing.lingo.remote.mapper

import org.brainail.everboxing.lingo.data.model.SearchResultEntity
import org.brainail.everboxing.lingo.remote.model.UrbanSearchResultRemoteEntity
import javax.inject.Inject

class UrbanSearchResultRemoteMapper @Inject constructor()
    : Mapper<UrbanSearchResultRemoteEntity, SearchResultEntity> {

    override fun mapFromRemote(input: UrbanSearchResultRemoteEntity): SearchResultEntity {
        return SearchResultEntity(input.defid, input.word, input.definition, input.example, input.permalink)
    }
}
