@file:JvmName("ContextUtils")

package org.brainail.everboxing.lingo.util.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import org.brainail.everboxing.lingo.app.App

val Context.app: App
    get() = applicationContext as App

fun Context.drawable(@DrawableRes drawable: Int) = ContextCompat.getDrawable(this, drawable)!!
fun Context.color(@ColorRes color: Int) = ContextCompat.getColor(this, color)