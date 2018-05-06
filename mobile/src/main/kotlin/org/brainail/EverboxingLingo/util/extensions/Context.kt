@file:JvmName("ContextUtils")

package org.brainail.EverboxingLingo.util.extensions

import android.content.Context
import org.brainail.EverboxingLingo.App

val Context.app: App
    get() = applicationContext as App