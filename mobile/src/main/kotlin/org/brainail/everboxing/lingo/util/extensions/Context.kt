@file:JvmName("ContextUtils")
@file:Suppress("NOTHING_TO_INLINE")

package org.brainail.everboxing.lingo.util.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import org.brainail.everboxing.lingo.app.App

inline val Context.app: App
    get() = applicationContext as App

inline fun Context.drawable(@DrawableRes drawable: Int) = ContextCompat.getDrawable(this, drawable)!!
inline fun Context.color(@ColorRes color: Int) = ContextCompat.getColor(this, color)
inline fun Context.pixelOffset(@DimenRes dimen: Int) = resources.getDimensionPixelOffset(dimen)
inline fun Context.pixelSize(@DimenRes dimen: Int) = resources.getDimensionPixelSize(dimen)
