package org.brainail.everboxing.lingo.util

sealed class TextToSpeechResult {
    class Successful(val text: String) : TextToSpeechResult()
    class Empty : TextToSpeechResult()
}
