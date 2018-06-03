package org.brainail.EverboxingLingo.remote.model

/**
 * ```
 * {"results":[...]}
 * ```
 */
data class UrbanSuggestionsResponse(val results: List<UrbanSuggestionRemoteEntity>)

/**
 * ```
 * {
 *      "preview":"The sound a cat makes",
 *      "term":"Meow"
 * }
 * ```
 */
data class UrbanSuggestionRemoteEntity(val term: String, val preview: String)