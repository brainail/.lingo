package org.brainail.EverboxingLingo.ui.search

import android.os.Parcel
import android.os.Parcelable
import paperparcel.PaperParcel

@PaperParcel
data class LingoSearchViewState(
        val loading: Boolean = false) : Parcelable {

    override fun describeContents() = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        PaperParcelLingoSearchViewState.writeToParcel(this, parcel, flags)
    }

    companion object {
        @JvmField
        @Suppress("unused")
        val CREATOR = PaperParcelLingoSearchViewState.CREATOR
    }
}