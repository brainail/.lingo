package org.brainail.EverboxingLingo.ui.base

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.annotation.LayoutRes
import org.brainail.EverboxingLingo.util.extensions.lazyFast
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
            bundle: Bundle, state: ViewModelSavedState?, type: Class<out BaseViewModel>) {
        state?.let { bundle.putParcelable(KEY_VIEW_MODEL_STATE + "_" + type.name, state) }
    }

    private companion object {
        const val KEY_VIEW_MODEL_STATE = "activity_view_model_state"
    }
}
