package org.brainail.EverboxingLingo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
class ViewModelSavedState(private val values: Bundle = Bundle()): Parcelable {
    fun put(key: String, value: Any): ViewModelSavedState {
        when (value) {
            is String -> values.putString(key, value)
        }
        return this
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String): T? {
        return values.get(key) as T?
    }
}