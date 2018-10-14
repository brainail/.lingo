package org.brainail.everboxing.lingo.domain.usecase

import io.reactivex.Completable
import org.brainail.everboxing.lingo.domain.executor.AppExecutors
import org.brainail.everboxing.lingo.domain.repository.SearchResultRepository
import javax.inject.Inject

class FavoriteSearchResultUseCase @Inject constructor(
        private val appExecutors: AppExecutors,
        private val searchResultRepository: SearchResultRepository) {

    fun execute(id: Int): Completable {
        return searchResultRepository.favoriteSearchResult(id)
                .compose(appExecutors.applyCompletableSchedulers())
    }
}