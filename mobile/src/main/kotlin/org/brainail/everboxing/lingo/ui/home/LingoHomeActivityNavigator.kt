/*
 * Copyright 2018 Malyshev Yegor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
