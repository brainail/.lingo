package org.brainail.EverboxingLingo.domain.usecase.connections

import io.reactivex.Completable
import org.brainail.EverboxingLingo.domain.connections.ConnectionsService
import org.brainail.EverboxingLingo.domain.executor.RxExecutor

class ConnectToServiceUseCase(
        private val rxExecutor: RxExecutor,
        private val connectionsService: ConnectionsService) {

    fun execute(): Completable = connectionsService
            .connect()
            .compose(rxExecutor.applyCompletableSchedulers())
}