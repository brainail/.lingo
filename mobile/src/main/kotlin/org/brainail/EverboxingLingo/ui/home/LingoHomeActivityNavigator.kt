package org.brainail.EverboxingLingo.ui.home

import android.support.v7.app.AppCompatActivity
import org.brainail.EverboxingLingo.R
import org.brainail.EverboxingLingo.navigator.Intents
import org.brainail.EverboxingLingo.navigator.SceneNavigator
import org.brainail.EverboxingLingo.ui.home.explore.LingoSearchFragment
import org.brainail.EverboxingLingo.util.NavigableBack
import org.brainail.EverboxingLingo.util.ScrollablePage
import org.brainail.EverboxingLingo.util.extensions.openFragment

class LingoHomeActivityNavigator(activity: AppCompatActivity) : SceneNavigator(activity) {
    fun closeScreen() {
        activity.supportFinishAfterTransition()
    }

    fun showTextToSpeech(prompt: String) = startActivityForResult(REQ_CODE_SPEECH_INPUT) {
        Intents.TextToSpeech.showVoiceRecognizer(prompt)
    }

    fun canShowTextToSpeech() =
            null != Intents.TextToSpeech.showVoiceRecognizer(null).resolveActivity(context.packageManager)

    fun showExploreSubScreen() {
        activity.openFragment(LingoSearchFragment.FRAGMENT_TAG, R.id.containerView) {
            LingoSearchFragment.newInstance()
        }
    }

    fun goBack() {
        val fragment = activity.supportFragmentManager.findFragmentById(R.id.containerView)
        if ((fragment as? NavigableBack)?.goBack() == false) {
            closeScreen()
        }
    }

    fun scrollToTop() {
        val fragment = activity.supportFragmentManager.findFragmentById(R.id.containerView)
        (fragment as? ScrollablePage)?.scrollToTop()
    }

    companion object {
        const val REQ_CODE_SPEECH_INPUT = 100
    }
}