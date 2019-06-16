/*
 * Copyright 2019 Malyshev Yegor
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

package org.brainail.everboxing.lingo.ui.home.details

import android.widget.TextView
import org.brainail.logger.L

class WordDetailsFragmentViewStateRenderer(
    private val wordTitleView: TextView,
    private val wordDefinitionView: TextView,
    private val wordExampleView: TextView
) {

    fun render(viewState: WordDetailsFragmentViewState) {
        L.i("render()")

        wordTitleView.text = viewState.wordTitle
        wordDefinitionView.text = viewState.wordDefinition
        wordExampleView.text = viewState.wordExample
    }
}
