package org.brainail.EverboxingLingo.domain.usecase

import io.reactivex.Observable
import org.brainail.EverboxingLingo.domain.executor.RxExecutor
import org.brainail.EverboxingLingo.domain.model.UserProfile
import org.brainail.EverboxingLingo.domain.repository.UserRepository

class GetUserProfileUseCase(
        private val rxExecutor: RxExecutor,
        private val userRepository: UserRepository) {

    fun execute(): Observable<UserProfile> = userRepository
            .getUserProfile()
            .compose(rxExecutor.applyObservableSchedulers())
}