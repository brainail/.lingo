@file:JvmName("ContextUtils")

package org.brainail.everboxing.lingo.util.extensions

import android.content.Context
import org.brainail.everboxing.lingo.app.App

val Context.app: App
    get() = applicationContext as App