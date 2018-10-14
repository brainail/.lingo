package org.brainail.everboxing.lingo.model

sealed class TextToSpeechResult {
    class Successful(val text: String) : TextToSpeechResult()
    class Empty : TextToSpeechResult()
}