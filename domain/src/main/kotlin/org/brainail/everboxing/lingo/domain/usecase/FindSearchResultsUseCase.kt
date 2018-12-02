package org.brainail.everboxing.lingo.domain.usecase

import io.reactivex.Flowable
import org.brainail.everboxing.lingo.domain.executor.AppExecutors
import org.brainail.everboxing.lingo.domain.model.SearchResult
import org.brainail.everboxing.lingo.domain.repository.SearchResultRepository
import javax.inject.Inject

class FindSearchResultsUseCase @Inject constructor(
        private val appExecutors: AppExecutors,
        private val searchResultRepository: SearchResultRepository) {

    fun execute(query: String): Flowable<List<SearchResult>> {
        return searchResultRepository.getSearchResults(query)
                .onErrorReturn { emptyList() }
                .map { it.filter { it.word.isNotBlank() } }
                .compose(appExecutors.applyFlowableBackgroundSchedulers())
    }
}
