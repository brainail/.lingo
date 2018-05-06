@file:JvmName("TextUtils")
@file:Suppress("NOTHING_TO_INLINE")

package org.brainail.EverboxingLingo.util.extensions

const val EMPTY_TEXT = ""

inline fun Char.compareCaselessTo(other: Char) = toLowerCase() == other.toLowerCase()

