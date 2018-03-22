package org.brainail.EverboxingLingo.data.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

internal interface UrbanApi {
    @GET("/v0/autocomplete")
    fun getSuggestions(@Query("term") term: String): Observable<Object>

    @GET("/v0/autocomplete-extra")
    fun getSuggestionsExtra(@Query("term") term: String): Observable<Object>
}