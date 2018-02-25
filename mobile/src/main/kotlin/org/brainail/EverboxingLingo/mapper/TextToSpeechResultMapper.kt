package org.brainail.EverboxingLingo.mapper

import android.content.Intent
import android.speech.RecognizerIntent
import android.support.v7.app.AppCompatActivity
import org.brainail.EverboxingLingo.model.TextToSpeechResult

internal class TextToSpeechResultMapper {
    fun transform(activityResultCode: Int, activityResultData: Intent?): TextToSpeechResult = when (activityResultCode) {
        AppCompatActivity.RESULT_OK -> activityResultData?.run {
            TextToSpeechResult.Successful(getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)[0])
        } ?: TextToSpeechResult.Empty()
        else -> TextToSpeechResult.Empty()
    }
}