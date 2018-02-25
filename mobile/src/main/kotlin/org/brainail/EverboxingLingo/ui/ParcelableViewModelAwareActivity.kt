package org.brainail.EverboxingLingo.ui

import android.os.Bundle

abstract class ParcelableViewModelAwareActivity<VM : BaseViewModel> : ViewModelAwareActivity<VM>() {

    private companion object {
        const val KEY_VIEW_MODEL_STATE = "view_model_state"
    }

    override fun getViewModelStateFromBundle(bundle: Bundle?): ViewModeSavedState? {
        return bundle?.getParcelable(KEY_VIEW_MODEL_STATE)
    }

    override fun saveViewModelStateToBundle(bundle: Bundle, state: ViewModeSavedState?) {
        state?.let { bundle.putParcelable(KEY_VIEW_MODEL_STATE, state) }
    }

}