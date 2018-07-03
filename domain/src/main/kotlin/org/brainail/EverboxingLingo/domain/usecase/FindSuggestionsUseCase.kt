package org.brainail.EverboxingLingo.domain.usecase

import io.reactivex.Flowable
import org.brainail.EverboxingLingo.domain.executor.AppExecutors
import org.brainail.EverboxingLingo.domain.model.Suggestion
import org.brainail.EverboxingLingo.domain.repository.SuggestionsRepository
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