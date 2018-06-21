package org.brainail.EverboxingLingo.domain.model

data class SearchResult(
        val id: Int,
        val definitionId: String,
        val word: String,
        val definition: String,
        val example: String,
        val link: String)