package org.brainail.EverboxingLingo.domain.event

sealed class GlobalEvents {
    data class UiStateChanged(val isOnForeground: Boolean) : GlobalEvents()
}