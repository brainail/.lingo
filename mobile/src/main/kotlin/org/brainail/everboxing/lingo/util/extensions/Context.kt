/*
 * Copyright 2018 Malyshev Yegor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
