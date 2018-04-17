package org.brainail.EverboxingLingo.remote

import io.reactivex.Single
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
}