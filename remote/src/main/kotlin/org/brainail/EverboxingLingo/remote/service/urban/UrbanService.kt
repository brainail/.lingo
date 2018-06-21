package org.brainail.EverboxingLingo.remote.service.urban

import io.reactivex.Single
import org.brainail.EverboxingLingo.remote.model.UrbanSearchResultsResponse
import org.brainail.EverboxingLingo.remote.model.UrbanSuggestionsResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * - @GET("/v0/autocomplete")
 * - @GET("/v0/autocomplete-extra")
 */
interface UrbanService {
    @GET("/v0/autocomplete-extra")
    fun getSuggestions(@Query("term") term: String): Single<UrbanSuggestionsResponse>

    @GET("/v0/define")
    fun getSearchResults(@Query("term") term: String): Single<UrbanSearchResultsResponse>

    // https://api.urbandictionary.com/v0/define with ?term=WORD_HERE or ?defid=DEFID_HERE
    // https://api.urbandictionary.com/v0/random
    // https://api.urbandictionary.com/v0/vote POST: {defid: 665139, direction: "up"}
    // https://api.urbandictionary.com/vote-buttons?defid=4487768
    // http://jisho.org/api/v1/search/words?keyword=%23jlpt-n1&page=2
    // https://www.quora.com/Is-there-a-free-to-use-dictionary-API
    // https://github.com/zdict/zdict
}