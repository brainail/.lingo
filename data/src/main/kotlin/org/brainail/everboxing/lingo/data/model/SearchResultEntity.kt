package org.brainail.everboxing.lingo.data.model

data class SearchResultEntity(
        val definitionId: String = "",
        val word: String = "",
        val definition: String = "",
        val example: String = "",
        val link: String = "",
        val id: Int = 0,
        val favorite: Boolean = false)