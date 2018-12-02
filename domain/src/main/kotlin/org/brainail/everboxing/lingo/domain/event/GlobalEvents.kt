package org.brainail.everboxing.lingo.domain.event

sealed class GlobalEvents {
    data class UiStateChanged(val isOnForeground: Boolean) : GlobalEvents()
}
