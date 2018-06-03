package org.brainail.EverboxingLingo.domain.usecase

import io.reactivex.Flowable
import org.brainail.EverboxingLingo.domain.executor.RxExecutor
import org.brainail.EverboxingLingo.domain.model.SearchResult
import org.brainail.EverboxingLingo.domain.repository.SearchResultRepository
import javax.inject.Inject

class FindSearchResultsUseCase @Inject constructor(
        private val rxExecutor: RxExecutor,
        private val searchResultRepository: SearchResultRepository) {

    fun execute(query: String): Flowable<List<SearchResult>> = searchResultRepository.getSearchResults(query)
            .onErrorReturn { emptyList() }
            .compose(rxExecutor.applyFlowableBackgroundSchedulers())
}