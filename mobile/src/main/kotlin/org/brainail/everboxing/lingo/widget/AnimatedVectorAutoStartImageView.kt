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

package org.brainail.everboxing.lingo.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Animatable
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView

class AnimatedVectorAutoStartImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    override fun onAttachedToWindow() {
        ensureVectorAnimation(true)
        super.onAttachedToWindow()
    }

    override fun onDraw(canvas: Canvas?) {
        ensureVectorAnimation(true)
        super.onDraw(canvas)
    }

    override fun onDetachedFromWindow() {
        ensureVectorAnimation(false)
        super.onDetachedFromWindow()
    }

    private fun ensureVectorAnimation(isActive: Boolean) {
        if (View.VISIBLE == visibility && isActive) {
            (drawable as? Animatable)?.apply {
                if (!isRunning) {
                    start()
                }
            }
        } else {
            (drawable as? Animatable)?.apply {
                if (isRunning) {
                    stop()
                }
            }
        }
    }
}
