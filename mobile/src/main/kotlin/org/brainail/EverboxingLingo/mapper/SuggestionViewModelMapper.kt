package org.brainail.EverboxingLingo.mapper

import org.brainail.EverboxingLingo.domain.model.Suggestion
import org.brainail.EverboxingLingo.model.SuggestionViewModel
import org.brainail.EverboxingLingo.util.ElasticSearchHighlighter
import javax.inject.Inject

class SuggestionViewModelMapper @Inject constructor(
        private val resultHighlighter: ElasticSearchHighlighter): Mapper<Suggestion, SuggestionViewModel> {

    override fun mapToViewModel(input: Suggestion): SuggestionViewModel {
        val highlightedWord: CharSequence = input.highlights?.let {
            resultHighlighter.makeHighlighted(input.word, it)
        } ?: input.word
        return SuggestionViewModel(highlightedWord, input.description.trim())
    }
}