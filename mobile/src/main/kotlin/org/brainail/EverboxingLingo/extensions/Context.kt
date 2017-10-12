package org.brainail.EverboxingLingo.extensions

import android.content.Context
import org.brainail.EverboxingLingo.App

val Context.app: App
        get() = applicationContext as App