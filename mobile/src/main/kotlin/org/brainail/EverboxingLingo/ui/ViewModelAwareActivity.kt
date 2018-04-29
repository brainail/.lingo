package org.brainail.EverboxingLingo.ui

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.LayoutRes
import javax.inject.Inject

abstract class ViewModelAwareActivity<VM : BaseViewModel> : BaseActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId())

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelType())
        viewModel.initState(getViewModelStateFromBundle(savedInstanceState))
    }

    @LayoutRes
    abstract fun layoutResId(): Int

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveViewModelStateToBundle(outState, viewModel.stateToSave)
    }

    protected open fun getViewModelStateFromBundle(bundle: Bundle?): ViewModelSavedState? {
        return null
    }

    protected open fun saveViewModelStateToBundle(bundle: Bundle, state: ViewModelSavedState?) {
        // No-op
    }

    abstract fun viewModelType(): Class<VM>
}
