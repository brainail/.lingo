package org.brainail.EverboxingLingo.ui.home

import android.arch.lifecycle.ViewModel
import org.brainail.logger.L
import javax.inject.Inject

class LingoHomeActivityViewModel @Inject constructor() : ViewModel() {
    init {
        @Suppress("LeakingThis")
        L.d("constructor(): $this")
    }

    override fun onCleared() {
        super.onCleared()
        L.d("onCleared(): $this")
    }
}