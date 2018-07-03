package org.brainail.EverboxingLingo.domain.model

data class Suggestion(
        val word: String,
        val description: String,
        val isRecent: Boolean = false,
        val id: Int = 0,
        val highlights: String? = null)