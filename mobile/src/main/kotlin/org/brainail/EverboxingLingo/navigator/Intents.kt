package org.brainail.EverboxingLingo.navigator

import android.content.Context
import org.brainail.EverboxingLingo.ui.home.LingoHomeActivity
import org.jetbrains.anko.intentFor

object Intents {
    object General {
        fun openLingoHome(context: Context) = context.intentFor<LingoHomeActivity>()
    }
}