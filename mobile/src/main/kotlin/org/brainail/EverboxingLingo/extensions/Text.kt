package org.brainail.EverboxingLingo.extensions

import android.text.Editable
import android.widget.EditText

inline fun EditText.moveCursorToEnd() {
    setSelection(length())
}

inline fun Editable.strim() = toString().trim()

inline fun CharSequence.strim() = toString().trim()