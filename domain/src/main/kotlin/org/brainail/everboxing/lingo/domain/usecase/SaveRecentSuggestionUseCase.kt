package org.brainail.everboxing.lingo.domain.usecase

import io.reactivex.Completable
import org.brainail.everboxing.lingo.domain.executor.AppExecutors
import org.brainail.everboxing.lingo.domain.model.Suggestion
import org.brainail.everboxing.lingo.domain.repository.SuggestionsRepository
import javax.inject.Inject

class SaveRecentSuggestionUseCase @Inject constructor(
        private val appExecutors: AppExecutors,
        private val suggestionsRepository: SuggestionsRepository) {

    fun execute(suggestion: Suggestion): Completable {
        return suggestionsRepository.saveSuggestionAsRecent(suggestion)
                .compose(appExecutors.applyCompletableSchedulers())
    }
}