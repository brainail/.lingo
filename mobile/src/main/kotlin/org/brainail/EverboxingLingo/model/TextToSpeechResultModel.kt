package org.brainail.EverboxingLingo.model

sealed class TextToSpeechResult {
    class Successful(val text: String) : TextToSpeechResult()
    class Empty : TextToSpeechResult()
}