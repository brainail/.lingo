package org.brainail.EverboxingLingo.domain.usecase

import io.reactivex.Completable
import org.brainail.EverboxingLingo.domain.executor.AppExecutors
import org.brainail.EverboxingLingo.domain.model.Suggestion
import org.brainail.EverboxingLingo.domain.repository.SuggestionsRepository
import javax.inject.Inject

class SaveRecentSuggestionUseCase @Inject constructor(
        private val appExecutors: AppExecutors,
        private val suggestionsRepository: SuggestionsRepository) {

    fun execute(suggestion: Suggestion): Completable {
        return suggestionsRepository.saveSuggestionAsRecent(suggestion)
                .compose(appExecutors.applyCompletableSchedulers())
    }
}