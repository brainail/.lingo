@file:JvmName("TextUtils")

package org.brainail.EverboxingLingo.extensions

import android.text.Editable

const val EMPTY_TEXT = ""

fun Editable.strim() = toString().trim()
fun CharSequence.strim() = toString().trim()

