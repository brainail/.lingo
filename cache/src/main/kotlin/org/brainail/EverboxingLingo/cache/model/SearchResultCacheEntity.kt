package org.brainail.EverboxingLingo.cache.model

data class SearchResultCacheEntity(
        val id: String,
        val word: String,
        val definition: String,
        val example: String)