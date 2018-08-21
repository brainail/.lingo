package org.brainail.everboxing.lingo.domain.usecase

import io.reactivex.Flowable
import org.brainail.everboxing.lingo.domain.executor.AppExecutors
import org.brainail.everboxing.lingo.domain.model.Suggestion
import org.brainail.everboxing.lingo.domain.repository.SuggestionsRepository
import javax.inject.Inject

class FindSuggestionsUseCase @Inject constructor(
        private val appExecutors: AppExecutors,
        private val suggestionsRepository: SuggestionsRepository) {

    fun execute(query: String): Flowable<List<Suggestion>> {
        return suggestionsRepository.getSuggestions(query)
                .onErrorReturn { emptyList() }
                .compose(appExecutors.applyFlowableBackgroundSchedulers())
    }
}