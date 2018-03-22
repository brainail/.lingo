package org.brainail.EverboxingLingo.data.repository

import android.os.SystemClock
import io.reactivex.Observable
import org.brainail.EverboxingLingo.domain.model.Suggestion
import org.brainail.EverboxingLingo.domain.repository.SuggestionsRepository

internal class SuggestionsRepositoryImpl() : SuggestionsRepository {
    override fun findSuggestions(query: String): Observable<List<Suggestion>> = Observable.fromCallable {
        // https://api.urbandictionary.com/v0/autocomplete?term=holymoly
        // https//api.urbandictionary.com/v0/autocomplete-extra?term=holymoly
        // https://api.urbandictionary.com/v0/define with ?term=WORD_HERE or ?defid=DEFID_HERE
        // https://api.urbandictionary.com/v0/random
        // https://api.urbandictionary.com/v0/vote POST: {defid: 665139, direction: "up"}
        SystemClock.sleep(2000)
        if (query.startsWith("ex")) {
            throw RuntimeException()
        } else {
            listOf(Suggestion("0. " + query, "1. " + query))
        }
    }
}