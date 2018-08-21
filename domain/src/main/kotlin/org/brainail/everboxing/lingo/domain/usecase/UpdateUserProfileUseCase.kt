package org.brainail.everboxing.lingo.domain.usecase

import io.reactivex.Observable
import org.brainail.everboxing.lingo.domain.executor.AppExecutors
import org.brainail.everboxing.lingo.domain.model.UserProfile
import org.brainail.everboxing.lingo.domain.repository.UserRepository

class UpdateUserProfileUseCase(
        private val appExecutors: AppExecutors,
        private val userRepository: UserRepository) {

    fun execute(userProfile: UserProfile): Observable<UserProfile> = userRepository
            .updateUserProfile(userProfile)
            .compose(appExecutors.applyObservableSchedulers())
}