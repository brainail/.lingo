package org.brainail.EverboxingLingo.app

import android.content.Context
import android.content.Intent
import org.brainail.EverboxingLingo.navigator.Navigator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class AppNavigator @Inject constructor(context: Context) : Navigator(context) {
    val addIntentFlags: Intent.() -> Unit = { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
}

