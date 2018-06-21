package org.brainail.EverboxingLingo.remote.model

data class UrbanSuggestionsResponse(val results: List<UrbanSuggestionRemoteEntity>)
data class UrbanSuggestionRemoteEntity(val term: String, val preview: String)