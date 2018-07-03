@file:JvmName("TextUtils")
@file:Suppress("NOTHING_TO_INLINE")

package org.brainail.EverboxingLingo.util.extensions

inline fun Char.compareCaselessTo(other: Char) = toLowerCase() == other.toLowerCase()

