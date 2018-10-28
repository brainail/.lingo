package org.brainail.everboxing.lingo.ui.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import org.brainail.everboxing.lingo.util.extensions.lazyFast
import javax.inject.Inject

abstract class ViewModelAwareFragment : BaseFragment() {
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
            bundle: Bundle, state: ViewModelSavedState?, type: Class<out BaseViewModel>) {
        state?.let { bundle.putParcelable(KEY_VIEW_MODEL_STATE + "_" + type.name, state) }
    }

    private companion object {
        const val KEY_VIEW_MODEL_STATE = "fragment_view_model_state"
    }
}
