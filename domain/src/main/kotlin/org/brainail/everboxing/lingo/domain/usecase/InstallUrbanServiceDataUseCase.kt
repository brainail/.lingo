package org.brainail.everboxing.lingo.domain.usecase

import io.reactivex.Completable
import org.brainail.everboxing.lingo.domain.executor.AppExecutors
import org.brainail.everboxing.lingo.domain.repository.SearchResultRepository
import org.brainail.everboxing.lingo.domain.settings.SyncSettings
import javax.inject.Inject

class InstallUrbanServiceDataUseCase @Inject constructor(
        private val appExecutors: AppExecutors,
        private val syncSettings: SyncSettings,
        private val searchResultRepository: SearchResultRepository) {

    fun execute(pathToData: String): Completable {
        if (syncSettings.isPreinstalledUrbanDataInitialized) {
            return Completable.complete()
        } else {
            syncSettings.isPreinstalledUrbanDataInitialized = true
        }

        return searchResultRepository.installUrbanSearchResult(pathToData)
                .compose(appExecutors.applyCompletableBackgroundSchedulers())
    }
}
