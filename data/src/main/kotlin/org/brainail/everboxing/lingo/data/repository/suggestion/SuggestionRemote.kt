package org.brainail.everboxing.lingo.data.repository.suggestion

import io.reactivex.Single
import org.brainail.everboxing.lingo.data.model.SuggestionEntity

interface SuggestionRemote {
    fun getSuggestions(query: String): Single<List<SuggestionEntity>>
}