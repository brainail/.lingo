package org.brainail.EverboxingLingo.remote.model

/**
 * ```
 * {"results":[...]}
 * ```
 */
data class UrbanSearchResultResponse(val results: List<UrbanSuggestionRemoteEntity>)

/**
 * ```
 * {
 *      "id":"...",
 *      "word":"...",
 *      "definition":"...",
 *      "example":"..."
 * }
 * ```
 */
data class UrbanSearchResultRemoteEntity(
        val id: String,
        val word: String,
        val definition: String,
        val example: String)