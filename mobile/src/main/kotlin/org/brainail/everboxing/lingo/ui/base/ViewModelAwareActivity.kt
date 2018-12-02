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

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import org.brainail.everboxing.lingo.base.util.lazyFast
import javax.inject.Inject

abstract class ViewModelAwareActivity : BaseActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModels: Array<BaseViewModel>? by lazyFast { createPrimaryViewModels() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())

        viewModels?.forEach {
            it.initState(getViewModelStateFromBundle(savedInstanceState, it::class.java))
        }
    }

    abstract fun createPrimaryViewModels(): Array<BaseViewModel>?

    @LayoutRes
    abstract fun getLayoutResourceId(): Int

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModels?.forEach {
            saveViewModelStateToBundle(outState, it.stateToSave, it::class.java)
        }
    }

    private fun getViewModelStateFromBundle(bundle: Bundle?, type: Class<out BaseViewModel>): ViewModelSavedState? {
        return bundle?.getParcelable(KEY_VIEW_MODEL_STATE + "_" + type.name)
    }

    private fun saveViewModelStateToBundle(
        bundle: Bundle,
        state: ViewModelSavedState?,
        type: Class<out BaseViewModel>
    ) {
        state?.let { bundle.putParcelable(KEY_VIEW_MODEL_STATE + "_" + type.name, state) }
    }

    private companion object {
        const val KEY_VIEW_MODEL_STATE = "activity_view_model_state"
    }
}
