package org.brainail.everboxing.lingo.ui.home

import androidx.appcompat.app.AppCompatActivity
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.navigator.Intents
import org.brainail.everboxing.lingo.navigator.SceneNavigator
import org.brainail.everboxing.lingo.ui.home.explore.LingoSearchFragment
import org.brainail.everboxing.lingo.util.NavigableBack
import org.brainail.everboxing.lingo.util.ScrollablePage
import org.brainail.everboxing.lingo.util.extensions.openFragment

class LingoHomeActivityNavigator(activity: AppCompatActivity) : SceneNavigator(activity) {
    fun showTextToSpeech(prompt: String) = startActivityForResult(REQ_CODE_SPEECH_INPUT) {
        Intents.TextToSpeech.showVoiceRecognizer(prompt)
    }

    fun canShowTextToSpeech() =
            null != Intents.TextToSpeech.showVoiceRecognizer(null).resolveActivity(context.packageManager)

    fun showExploreSubScreen() {
        activity.openFragment(LingoSearchFragment.layoutTag, R.id.containerView) {
            LingoSearchFragment.newInstance()
        }
    }

    fun goBack() {
        val fragment = activity.supportFragmentManager.findFragmentById(R.id.containerView)
        if ((fragment as? NavigableBack)?.goBack() != true) {
            if (activity.supportFragmentManager.backStackEntryCount > 0) {
                activity.supportFragmentManager.popBackStack()
            } else {
                activity.supportFinishAfterTransition()
            }
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