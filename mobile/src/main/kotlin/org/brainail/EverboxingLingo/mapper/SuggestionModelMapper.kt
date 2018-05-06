package org.brainail.EverboxingLingo.mapper

import org.brainail.EverboxingLingo.domain.model.Suggestion
import org.brainail.EverboxingLingo.model.SuggestionModel
import org.brainail.EverboxingLingo.util.ElasticSearchHighlighter
import javax.inject.Inject

class SuggestionModelMapper @Inject constructor(
        private val resultHighlighter: ElasticSearchHighlighter): Mapper<Suggestion, SuggestionModel> {

    override fun mapToModel(input: Suggestion): SuggestionModel {
        val highlightedWord: CharSequence = input.highlights?.let {
            resultHighlighter.makeHighlighted(input.word, it)
        } ?: input.word
        return SuggestionModel(highlightedWord, input.description.trim())
    }
}