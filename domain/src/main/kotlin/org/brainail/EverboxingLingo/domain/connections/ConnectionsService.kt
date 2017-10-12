package org.brainail.EverboxingLingo.domain.connections

import io.reactivex.Completable

interface ConnectionsService {
    enum class Status {
        CONNECTED,
        CONNECTING,
        DISCONNECTED,
        RESOLVING_ERROR_REQUESTED,
        RESOLVING_ERROR,
        ERROR
    }

    fun connect(): Completable
    fun disconnect(): Completable
}