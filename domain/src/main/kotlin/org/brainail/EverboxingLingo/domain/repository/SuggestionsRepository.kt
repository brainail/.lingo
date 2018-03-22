package org.brainail.EverboxingLingo.domain.repository

import io.reactivex.Observable
import org.brainail.EverboxingLingo.domain.model.Suggestion

interface SuggestionsRepository {
    fun findSuggestions(query: String): Observable<List<Suggestion>>
}