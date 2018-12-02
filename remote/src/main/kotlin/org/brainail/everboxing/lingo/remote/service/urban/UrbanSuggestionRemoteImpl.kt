package org.brainail.everboxing.lingo.remote.service.urban

import io.reactivex.Single
import org.brainail.everboxing.lingo.data.model.SuggestionEntity
import org.brainail.everboxing.lingo.data.repository.suggestion.SuggestionRemote
import org.brainail.everboxing.lingo.remote.mapper.UrbanSuggestionRemoteMapper
import org.brainail.loggerrx.logEvents
import javax.inject.Inject

class UrbanSuggestionRemoteImpl @Inject constructor(
        private val urbanService: UrbanService,
        private val entityMapper: UrbanSuggestionRemoteMapper) : SuggestionRemote {

    override fun getSuggestions(query: String): Single<List<SuggestionEntity>> {
        return urbanService.getSuggestions(query)
                .logEvents()
                .map {
                    it.results.map { listItem ->
                        entityMapper.mapFromRemote(listItem)
                    }
                }
    }
}
