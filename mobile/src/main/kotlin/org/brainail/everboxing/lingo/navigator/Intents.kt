package org.brainail.everboxing.lingo.navigator

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import org.brainail.everboxing.lingo.ui.home.LingoHomeActivity
import org.jetbrains.anko.intentFor
import java.util.Locale

object Intents {
    object General {
        fun openLingoHome(context: Context) = context.intentFor<LingoHomeActivity>()
    }

    object TextToSpeech {
        fun showVoiceRecognizer(prompt: String?): Intent {
            return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                putExtra(RecognizerIntent.EXTRA_PROMPT, prompt)
            }
        }
    }
}