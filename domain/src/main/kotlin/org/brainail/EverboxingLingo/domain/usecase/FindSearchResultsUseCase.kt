package org.brainail.EverboxingLingo.domain.usecase

import io.reactivex.Flowable
import org.brainail.EverboxingLingo.domain.executor.AppExecutors
import org.brainail.EverboxingLingo.domain.model.SearchResult
import org.brainail.EverboxingLingo.domain.repository.SearchResultRepository
import javax.inject.Inject

class FindSearchResultsUseCase @Inject constructor(
        private val appExecutors: AppExecutors,
        private val searchResultRepository: SearchResultRepository) {

    fun execute(query: String): Flowable<List<SearchResult>> {
        return searchResultRepository.getSearchResults(query)
                .onErrorReturn { emptyList() }
                .filter { it.isNotEmpty() }
                .compose(appExecutors.applyFlowableBackgroundSchedulers())
    }
}