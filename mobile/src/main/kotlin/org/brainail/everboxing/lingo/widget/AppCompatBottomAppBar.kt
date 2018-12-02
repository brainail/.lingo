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
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomappbar.BottomAppBar
import org.brainail.everboxing.lingo.base.util.lazyFast

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
