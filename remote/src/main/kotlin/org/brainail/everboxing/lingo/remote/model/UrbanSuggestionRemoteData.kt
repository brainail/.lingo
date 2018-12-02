package org.brainail.everboxing.lingo.remote.model

data class UrbanSuggestionsResponse(val results: List<UrbanSuggestionRemoteEntity>)
data class UrbanSuggestionRemoteEntity(val term: String, val preview: String)
