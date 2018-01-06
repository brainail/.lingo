package org.brainail.EverboxingLingo.ui

import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper
import org.brainail.logger.L

abstract class BaseViewModel : ViewModel() {

    val stateToSave: ViewModeSavedState?
        get() = saveState()

    private var isCleared = false

    init {
        @Suppress("LeakingThis")
        L.d("constructor(): $this")
    }

    open fun initState(viewModelSavedState: ViewModeSavedState?) {
        // No-op
    }

    @CallSuper
    open fun saveState() = ViewModeSavedState()

    final override fun onCleared() {
        // sometimes we can get the same model using different keys and
        // in this case onCleared called several times
        // but because this model is shared then we don't need to do it more than once
        if (isCleared) {
            return
        }

        isCleared = true
        super.onCleared()
        clear()
    }

    @CallSuper
    open fun clear() {
        L.d("clear(): $this")
    }

}