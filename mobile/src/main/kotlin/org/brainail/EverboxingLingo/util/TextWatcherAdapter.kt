package org.brainail.EverboxingLingo.util

import android.text.Editable
import android.text.TextWatcher

open class TextWatcherAdapter: TextWatcher {
    override fun afterTextChanged(p0: Editable) {
        // Do nothing
    }

    override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
        // Do nothing
    }

    override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
        // Do nothing
    }
}