package org.brainail.everboxing.lingo.model

data class SuggestionModel(
        val word: CharSequence,
        val description: String = "",
        val isRecent: Boolean = false,
        val id: Int = 0,
        val isSilent: Boolean = false)
