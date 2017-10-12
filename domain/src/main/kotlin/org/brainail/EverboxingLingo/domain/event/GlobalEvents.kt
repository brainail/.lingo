package org.brainail.EverboxingLingo.domain.event

import org.brainail.EverboxingLingo.domain.connections.ConnectionsService

sealed class GlobalEvents {
    data class UiStateChanged(val isOnForeground: Boolean) : GlobalEvents()
    data class ConnectionServiceStatusChanged(val status: ConnectionsService.Status) : GlobalEvents()
}