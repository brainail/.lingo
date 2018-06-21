package org.brainail.EverboxingLingo.model

data class SearchResultModel(
        val id: Int,
        val definitionId :String,
        val word: String,
        val definition: String,
        val example: String,
        val link: String)