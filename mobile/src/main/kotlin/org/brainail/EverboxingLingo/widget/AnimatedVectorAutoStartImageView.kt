package org.brainail.EverboxingLingo.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Animatable
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View

/**
 * This file is part of Everboxing modules. <br/><br/>
 *
 * The MIT License (MIT) <br/><br/>
 *
 * Copyright (c) 2017 Malyshev Yegor aka brainail at org.brainail.everboxing@gmail.com <br/><br/>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy <br/>
 * of this software and associated documentation files (the "Software"), to deal <br/>
 * in the Software without restriction, including without limitation the rights <br/>
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell <br/>
 * copies of the Software, and to permit persons to whom the Software is <br/>
 * furnished to do so, subject to the following conditions: <br/><br/>
 *
 * The above copyright notice and this permission notice shall be included in <br/>
 * all copies or substantial portions of the Software. <br/><br/>
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR <br/>
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, <br/>
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE <br/>
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER <br/>
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, <br/>
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN <br/>
 * THE SOFTWARE.
 */
class AnimatedVectorAutoStartImageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
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