package org.brainail.EverboxingLingo.domain.usecase

import io.reactivex.Observable
import org.brainail.EverboxingLingo.domain.executor.AppExecutors
import org.brainail.EverboxingLingo.domain.model.UserProfile
import org.brainail.EverboxingLingo.domain.repository.UserRepository

class GetUserProfileUseCase(
        private val appExecutors: AppExecutors,
        private val userRepository: UserRepository) {

    fun execute(): Observable<UserProfile> = userRepository
            .getUserProfile()
            .compose(appExecutors.applyObservableSchedulers())
}