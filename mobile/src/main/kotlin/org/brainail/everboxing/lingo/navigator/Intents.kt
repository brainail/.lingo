package org.brainail.everboxing.lingo.navigator

import android.content.Intent
import android.speech.RecognizerIntent
import java.util.Locale

object Intents {
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