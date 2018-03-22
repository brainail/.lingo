package org.brainail.EverboxingLingo.domain.usecase

import io.reactivex.Observable
import org.brainail.EverboxingLingo.domain.executor.RxExecutor
import org.brainail.EverboxingLingo.domain.model.Suggestion
import org.brainail.EverboxingLingo.domain.repository.SuggestionsRepository
import javax.inject.Inject

class FindSuggestionsUseCase @Inject constructor(
        private val rxExecutor: RxExecutor,
        private val suggestionsRepository: SuggestionsRepository) {

    fun execute(query: String): Observable<List<Suggestion>> = suggestionsRepository.findSuggestions(query)
            .onErrorReturn { emptyList() }
            .compose(rxExecutor.applyObservableSchedulers())
}