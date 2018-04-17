package org.brainail.EverboxingLingo.mapper

import org.brainail.EverboxingLingo.domain.model.Suggestion
import org.brainail.EverboxingLingo.model.SuggestionViewModel
import javax.inject.Inject

class SuggestionViewModelMapper @Inject constructor(): Mapper<Suggestion, SuggestionViewModel> {
    override fun mapToViewModel(input: Suggestion): SuggestionViewModel {
        return SuggestionViewModel(input.word, input.description)
    }
}