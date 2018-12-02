package org.brainail.everboxing.lingo.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Animatable
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView

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
