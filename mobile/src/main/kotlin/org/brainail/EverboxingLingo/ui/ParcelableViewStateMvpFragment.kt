package org.brainail.EverboxingLingo.ui

import android.os.Bundle
import android.os.Parcelable

open class ParcelableViewStateMvpFragment<V, VS : Parcelable, P : Presenter<V, VS>> :
        MvpFragment<V, VS, P>() {

    companion object {
        val KEY_VIEW_STATE = "ParcelableViewStateMvpFragment_view_state"
    }

    override fun getViewStateFromBundle(bundle: Bundle?): VS? {
        return bundle?.getParcelable(KEY_VIEW_STATE)
    }

    override fun saveViewStateToBundle(bundle: Bundle, state: VS?) {
        state?.let { bundle.putParcelable(KEY_VIEW_STATE, state) }
    }
}