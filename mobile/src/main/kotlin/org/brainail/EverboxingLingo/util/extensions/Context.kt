@file:JvmName("ContextUtils")

package org.brainail.EverboxingLingo.util.extensions

import android.content.Context
import org.brainail.EverboxingLingo.app.App

val Context.app: App
    get() = applicationContext as App