package org.brainail.everboxing.lingo.domain.usecase

import io.reactivex.Single
import org.brainail.everboxing.lingo.domain.executor.AppExecutors
import org.brainail.everboxing.lingo.domain.repository.UserRepository

class GetDefaultUserNameUseCase(
        private val appExecutors: AppExecutors,
        private val userRepository: UserRepository) {

    fun execute(): Single<String> = userRepository
            .getDefaultUserName()
            .compose(appExecutors.applySingleSchedulers())
}