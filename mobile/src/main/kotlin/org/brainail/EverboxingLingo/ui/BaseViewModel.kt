package org.brainail.EverboxingLingo.ui

import android.arch.lifecycle.ViewModel
import org.brainail.logger.L

open class BaseViewModel : ViewModel() {

    init {
        @Suppress("LeakingThis")
        L.d("constructor(): $this")
    }

    override fun onCleared() {
        super.onCleared()
        L.d("onCleared(): $this")
    }

}