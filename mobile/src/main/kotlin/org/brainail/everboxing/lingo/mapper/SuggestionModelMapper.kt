package org.brainail.everboxing.lingo.mapper

import org.brainail.everboxing.lingo.domain.model.Suggestion
import org.brainail.everboxing.lingo.model.SuggestionModel
import org.brainail.everboxing.lingo.util.ElasticSearchHighlighter
import javax.inject.Inject

class SuggestionModelMapper @Inject constructor(
        private val resultHighlighter: ElasticSearchHighlighter): Mapper<Suggestion, SuggestionModel> {

    override fun mapToModel(input: Suggestion): SuggestionModel {
        val highlightedWord: CharSequence = input.highlights?.let {
            resultHighlighter.makeHighlighted(input.word, it)
        } ?: input.word
        return SuggestionModel(highlightedWord, input.description, input.isRecent, input.id)
    }

    override fun mapFromModel(input: SuggestionModel): Suggestion {
        return Suggestion(input.word.toString(), input.description, input.isRecent, input.id)
    }
}