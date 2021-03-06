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

package org.brainail.everboxing.lingo.util.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import org.brainail.everboxing.lingo.base.util.lazyFast
import org.brainail.everboxing.lingo.util.extensions.color
import org.brainail.everboxing.lingo.util.extensions.drawable

abstract class SwipeToActionCallback(
    context: Context,
    iconLeftInfo: IconInfo? = null,
    iconRightInfo: IconInfo? = null
) : SimpleCallback(
    0, (if (iconLeftInfo != null) ItemTouchHelper.LEFT else 0)
        or (if (iconRightInfo != null) ItemTouchHelper.RIGHT else 0)
) {

    private val iconLeft by lazyFast { iconLeftInfo?.run { context.drawable(resId) } }
    private val backgroundLeft by lazyFast { iconLeftInfo?.run { context.color(backgroundId) } ?: 0 }
    private val iconLeftWidth by lazyFast { iconLeft?.intrinsicWidth ?: 0 }
    private val iconLeftHeight by lazyFast { iconLeft?.intrinsicHeight ?: 0 }
    private val iconLeftOffset by lazyFast { iconLeftInfo?.sideOffset ?: 0 }
    private val iconRight by lazyFast { iconRightInfo?.run { context.drawable(resId) } }
    private val backgroundRight by lazyFast { iconRightInfo?.run { context.color(backgroundId) } ?: 0 }
    private val iconRightWidth by lazyFast { iconRight?.intrinsicWidth ?: 0 }
    private val iconRightHeight by lazyFast { iconRight?.intrinsicHeight ?: 0 }
    private val iconRightOffset by lazyFast { iconRightInfo?.sideOffset ?: 0 }
    private val background = ColorDrawable()

    private var left = 0
    private var right = 0
    private var top = 0
    private var bottom = 0
    private var height = 0

    private lateinit var icon: Drawable

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        when {
            dX < 0 -> drawRightIcon(canvas, dX, viewHolder.itemView)
            dX > 0 -> drawLeftIcon(canvas, dX, viewHolder.itemView)
        }

        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun drawRightIcon(canvas: Canvas, dX: Float, itemView: View) {
        icon = iconRight ?: return
        height = itemView.bottom - itemView.top

        // draw background
        background.color = backgroundRight
        background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        background.draw(canvas)

        // calc position of icon
        top = itemView.top + (height - iconRightHeight) / 2
        right = itemView.right - iconRightOffset
        left = right - iconRightWidth
        bottom = top + iconRightHeight

        // draw the icon
        icon.setBounds(left, top, right, bottom)
        icon.draw(canvas)
    }

    private fun drawLeftIcon(canvas: Canvas, dX: Float, itemView: View) {
        icon = iconLeft ?: return
        height = itemView.bottom - itemView.top

        // draw background
        background.color = backgroundLeft
        background.setBounds(itemView.left, itemView.top, itemView.left + dX.toInt(), itemView.bottom)
        background.draw(canvas)

        // calc position of icon
        top = itemView.top + (height - iconLeftHeight) / 2
        left = itemView.left + iconLeftOffset
        right = left + iconLeftWidth
        bottom = top + iconRightHeight

        // draw the icon
        icon.setBounds(left, top, right, bottom)
        icon.draw(canvas)
    }

    class IconInfo(@DrawableRes val resId: Int, @ColorRes val backgroundId: Int, @Px val sideOffset: Int)
}
