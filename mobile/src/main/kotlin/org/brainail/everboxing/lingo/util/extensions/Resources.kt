@file:JvmName("ResourceUtils")

package org.brainail.everboxing.lingo.util.extensions

import android.content.res.Resources
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat

fun Resources.color(@ColorRes color: Int, theme: Resources.Theme? = null) =
        ResourcesCompat.getColor(this, color, theme)

fun Resources.drawable(@DrawableRes drawable: Int, theme: Resources.Theme? = null) =
        ResourcesCompat.getDrawable(this, drawable, theme)!!