package org.brainail.EverboxingLingo.domain.usecase

import io.reactivex.Single
import org.brainail.EverboxingLingo.domain.executor.AppExecutors
import org.brainail.EverboxingLingo.domain.repository.UserRepository

class GetDefaultUserNameUseCase(
        private val appExecutors: AppExecutors,
        private val userRepository: UserRepository) {

    fun execute(): Single<String> = userRepository
            .getDefaultUserName()
            .compose(appExecutors.applySingleSchedulers())
}