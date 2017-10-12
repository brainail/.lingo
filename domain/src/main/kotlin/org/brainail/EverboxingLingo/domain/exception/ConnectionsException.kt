package org.brainail.EverboxingLingo.domain.exception

class ConnectionsException(val errorCode: Int) : Exception() {
    override fun toString(): String {
        return "ConnectionsException(errorCode=$errorCode)"
    }
}