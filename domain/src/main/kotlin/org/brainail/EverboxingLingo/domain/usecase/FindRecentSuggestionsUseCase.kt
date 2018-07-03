package org.brainail.EverboxingLingo.domain.usecase

import io.reactivex.Flowable
import org.brainail.EverboxingLingo.domain.executor.AppExecutors
import org.brainail.EverboxingLingo.domain.model.Suggestion
import org.brainail.EverboxingLingo.domain.repository.SuggestionsRepository
import javax.inject.Inject

class FindRecentSuggestionsUseCase @Inject constructor(
        private val appExecutors: AppExecutors,
        private val suggestionsRepository: SuggestionsRepository) {

    fun execute(query: String): Flowable<List<Suggestion>> {
        return suggestionsRepository.getRecentSuggestions(query, NUMBER_OF_RECENT_SUGGESTIONS)
                .onErrorReturn { emptyList() }
                .compose(appExecutors.applyFlowableBackgroundSchedulers())
    }

    companion object {
        private const val NUMBER_OF_RECENT_SUGGESTIONS = 3
    }
}