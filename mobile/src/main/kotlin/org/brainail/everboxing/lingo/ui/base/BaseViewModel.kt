/*
 * Copyright 2018 Malyshev Yegor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brainail.everboxing.lingo.ui.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
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
        ++restoreStateCount
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
