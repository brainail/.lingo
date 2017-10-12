package org.brainail.EverboxingLingo.domain.usecase

import io.reactivex.Single
import org.brainail.EverboxingLingo.domain.executor.RxExecutor
import org.brainail.EverboxingLingo.domain.repository.UserRepository

class GetDefaultUserNameUseCase(
        private val rxExecutor: RxExecutor,
        private val userRepository: UserRepository) {

    fun execute(): Single<String> = userRepository
            .getDefaultUserName()
            .compose(rxExecutor.applySingleSchedulers())
}