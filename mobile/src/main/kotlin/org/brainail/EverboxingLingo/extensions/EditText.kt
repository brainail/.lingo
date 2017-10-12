package org.brainail.EverboxingLingo.extensions

import android.widget.EditText

fun EditText.moveCursorToEnd() {
    setSelection(length())
}