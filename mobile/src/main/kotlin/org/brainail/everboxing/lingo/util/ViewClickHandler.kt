package org.brainail.everboxing.lingo.util

import androidx.annotation.IdRes

interface ViewClickHandler {
    fun handleViewClick(@IdRes viewId: Int): Boolean = false
}
