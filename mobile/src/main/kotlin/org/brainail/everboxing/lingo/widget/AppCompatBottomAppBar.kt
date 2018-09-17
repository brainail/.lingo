package org.brainail.everboxing.lingo.widget

import android.content.Context
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomappbar.BottomAppBar
import org.brainail.everboxing.lingo.util.extensions.lazyFast

class AppCompatBottomAppBar : BottomAppBar {

    private val compatBehavior by lazyFast { CompatBehavior() }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) // respect internal style
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getBehavior(): CoordinatorLayout.Behavior<BottomAppBar> {
        return compatBehavior
    }

    private inner class CompatBehavior : BottomAppBar.Behavior() {
        fun show() {
            slideUp(this@AppCompatBottomAppBar)
        }

        fun hide() {
            slideDown(this@AppCompatBottomAppBar)
        }
    }

    fun show() {
        compatBehavior.show()
    }

    fun hide() {
        compatBehavior.hide()
    }
}