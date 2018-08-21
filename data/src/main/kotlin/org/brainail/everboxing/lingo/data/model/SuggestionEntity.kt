package org.brainail.everboxing.lingo.data.model

data class SuggestionEntity(
        val word: String = "",
        val description: String = "",
        val isRecent: Boolean = false,
        val id: Int = 0)