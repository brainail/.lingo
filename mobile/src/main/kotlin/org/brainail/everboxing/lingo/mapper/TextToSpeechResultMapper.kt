package org.brainail.everboxing.lingo.mapper

import android.content.Intent
import android.speech.RecognizerIntent
import androidx.appcompat.app.AppCompatActivity
import org.brainail.everboxing.lingo.model.TextToSpeechResult

class TextToSpeechResultMapper {
    fun transform(activityResultCode: Int, activityResultData: Intent?): TextToSpeechResult = when (activityResultCode) {
        AppCompatActivity.RESULT_OK -> activityResultData?.run {
            TextToSpeechResult.Successful(getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)[0])
        } ?: TextToSpeechResult.Empty()
        else -> TextToSpeechResult.Empty()
    }
}