package org.brainail.EverboxingLingo.domain.usecase

import io.reactivex.Observable
import org.brainail.EverboxingLingo.domain.executor.AppExecutors
import org.brainail.EverboxingLingo.domain.model.UserProfile
import org.brainail.EverboxingLingo.domain.repository.UserRepository

class UpdateUserProfileUseCase(
        private val appExecutors: AppExecutors,
        private val userRepository: UserRepository) {

    fun execute(userProfile: UserProfile): Observable<UserProfile> = userRepository
            .updateUserProfile(userProfile)
            .compose(appExecutors.applyObservableSchedulers())
}