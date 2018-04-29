package org.brainail.EverboxingLingo.ui

import android.os.Bundle

abstract class ParcelableViewModelAwareFragment<VM : BaseViewModel> : ViewModelAwareFragment<VM>() {

    companion object {
        const val KEY_VIEW_MODEL_STATE = "ParcelableViewModelAwareFragment.ViewModelState.Key"
    }

    override fun getViewModelStateFromBundle(bundle: Bundle?): ViewModelSavedState? {
        return bundle?.getParcelable(KEY_VIEW_MODEL_STATE)
    }

    override fun saveViewModelStateToBundle(bundle: Bundle, state: ViewModelSavedState?) {
        state?.let { bundle.putParcelable(KEY_VIEW_MODEL_STATE, state) }
    }

}