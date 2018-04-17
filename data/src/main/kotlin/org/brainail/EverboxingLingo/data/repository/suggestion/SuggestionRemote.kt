package org.brainail.EverboxingLingo.data.repository.suggestion

import io.reactivex.Single
import org.brainail.EverboxingLingo.data.model.SuggestionEntity

interface SuggestionRemote {
    fun getSuggestions(query: String): Single<List<SuggestionEntity>>
}