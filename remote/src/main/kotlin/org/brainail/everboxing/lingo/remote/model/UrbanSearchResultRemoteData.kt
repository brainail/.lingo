package org.brainail.everboxing.lingo.remote.model

data class UrbanSearchResultsResponse(val list: List<UrbanSearchResultRemoteEntity>)
data class UrbanSearchResultRemoteEntity(
        val defid: String,
        val permalink: String,
        val word: String,
        val definition: String,
        val example: String)