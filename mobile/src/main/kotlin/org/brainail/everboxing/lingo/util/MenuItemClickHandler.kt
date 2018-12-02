package org.brainail.everboxing.lingo.util

import androidx.annotation.IdRes

interface MenuItemClickHandler {
    fun handleMenuItemClick(@IdRes menuId: Int): Boolean = false
}
