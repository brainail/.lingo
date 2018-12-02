package org.brainail.everboxing.lingo.ui.home

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.navigator.Intents
import org.brainail.everboxing.lingo.navigator.SceneNavigator
import org.brainail.everboxing.lingo.util.NavigableBack
import org.brainail.everboxing.lingo.util.ScrollablePage
import org.brainail.everboxing.lingo.util.extensions.getNavigationTopFragment

/**
 * Navigates to other screens from [LingoHomeActivity]
 */
class LingoHomeActivityNavigator(activity: AppCompatActivity) : SceneNavigator(activity) {
    fun showTextToSpeech(@StringRes promptId: Int) = startActivityForResult(REQ_CODE_SPEECH_INPUT) {
        Intents.TextToSpeech.showVoiceRecognizer(activity.getString(promptId))
    }

    fun showExplorePage() {
        val navController = activity.findNavController(R.id.lingoHomeNavigationFragment)
        if (navController.currentDestination?.id != R.id.explorePageDestination) {
            navController.navigate(R.id.explorePageDestination)
        }
    }

    fun goBack() {
        val fragment = activity.getNavigationTopFragment(R.id.lingoHomeNavigationFragment)
        if ((fragment as? NavigableBack)?.goBack() != true) {
            if (!activity.findNavController(R.id.lingoHomeNavigationFragment).navigateUp()) {
                activity.supportFinishAfterTransition()
            }
        }
    }

    fun scrollToTop() {
        val fragment = activity.getNavigationTopFragment(R.id.lingoHomeNavigationFragment)
        (fragment as? ScrollablePage)?.scrollToTop()
    }

    companion object {
        const val REQ_CODE_SPEECH_INPUT = 100

        @JvmStatic
        fun canShowTextToSpeech(context: Context) =
                null != Intents.TextToSpeech.showVoiceRecognizer(null).resolveActivity(context.packageManager)
    }
}
