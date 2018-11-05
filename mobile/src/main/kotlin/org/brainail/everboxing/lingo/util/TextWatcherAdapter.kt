package org.brainail.everboxing.lingo.util

import android.text.Editable
import android.text.TextWatcher

open class TextWatcherAdapter : TextWatcher {
    override fun afterTextChanged(query: Editable) = Unit
    override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) = Unit
    override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) = Unit
}