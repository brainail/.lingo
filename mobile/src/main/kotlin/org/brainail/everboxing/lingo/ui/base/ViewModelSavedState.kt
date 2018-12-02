package org.brainail.everboxing.lingo.ui.base

import android.os.Bundle
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ViewModelSavedState(private val values: Bundle = Bundle()): Parcelable {
    fun put(key: String, value: Any): ViewModelSavedState {
        when (value) {
            is String -> values.putString(key, value)
            is Int -> values.putInt(key, value)
            is Long -> values.putLong(key, value)
        }
        return this
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String): T? {
        return values.get(key) as T?
    }
}
