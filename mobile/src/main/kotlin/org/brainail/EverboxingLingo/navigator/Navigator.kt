package org.brainail.EverboxingLingo.navigator

import android.content.Context
import android.content.Intent

abstract class Navigator(protected val context: Context) {
    protected inline fun startActivity(intentFactory: () -> Intent) {
        context.startActivity(intentFactory())
    }
}