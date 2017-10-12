package org.brainail.EverboxingLingo

import android.content.Context
import android.content.Intent
import org.brainail.EverboxingLingo.navigator.Navigator

open class AppNavigator(context: Context) : Navigator(context) {
    val addIntentFlags: Intent.() -> Unit = { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
}

