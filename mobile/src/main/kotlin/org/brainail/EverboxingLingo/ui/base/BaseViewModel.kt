package org.brainail.EverboxingLingo.ui.base

import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper
import org.brainail.logger.L

abstract class BaseViewModel : ViewModel() {

    val stateToSave: ViewModelSavedState?
        get() = saveState()

    private var isCleared = false
    private var restoreStateCount = 0

    init {
        @Suppress("LeakingThis")
        L.d("constructor(): $this")
    }

    @CallSuper
    open fun initState(viewModelSavedState: ViewModelSavedState?) {
        ++ restoreStateCount
    }

    protected fun isFirstRestore() = 1 == restoreStateCount

    @CallSuper
    open fun saveState() = ViewModelSavedState()

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