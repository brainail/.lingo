package org.brainail.EverboxingLingo.ui.base

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import org.brainail.EverboxingLingo.util.extensions.lazyFast
import javax.inject.Inject

abstract class ViewModelAwareFragment<VM : BaseViewModel> : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModels: Array<BaseViewModel>? by lazyFast { createPrimaryViewModels() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModels?.forEach {
            it.initState(getViewModelStateFromBundle(savedInstanceState, it::class.java))
        }
    }

    abstract fun createPrimaryViewModels(): Array<BaseViewModel>?

    private fun getViewModelStateFromBundle(bundle: Bundle?, type: Class<out BaseViewModel>): ViewModelSavedState? {
        return bundle?.getParcelable(KEY_VIEW_MODEL_STATE + "_" + type.name)
    }

    private fun saveViewModelStateToBundle(
            bundle: Bundle, state: ViewModelSavedState?, type: Class<out BaseViewModel>) {
        state?.let { bundle.putParcelable(KEY_VIEW_MODEL_STATE + "_" + type.name, state) }
    }

    private companion object {
        const val KEY_VIEW_MODEL_STATE = "fragment_view_model_state"
    }
}
