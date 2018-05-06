package org.brainail.EverboxingLingo.util.paperParcel.adapter

import android.os.Parcel
import org.brainail.EverboxingLingo.util.paperParcel.StubParcelable
import paperparcel.TypeAdapter

class StubParcelableAdapter<T : StubParcelable> : TypeAdapter<T?> {
    override fun readFromParcel(source: Parcel): T? {
        return null
    }

    override fun writeToParcel(value: T?, dest: Parcel, flags: Int) {
        // do nothing
    }
}