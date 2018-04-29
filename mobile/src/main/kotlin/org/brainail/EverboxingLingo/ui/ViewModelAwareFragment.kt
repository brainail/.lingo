package org.brainail.EverboxingLingo.ui

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import javax.inject.Inject

abstract class ViewModelAwareFragment<VM : BaseViewModel> : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    protected lateinit var viewModel: VM

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelType())
        viewModel.initState(getViewModelStateFromBundle(savedInstanceState))
    }

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
