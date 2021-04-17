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

import org.brainail.everboxing.lingo.base.util.lazyFast
import org.brainail.everboxing.lingo.model.SearchResultModel
import org.brainail.everboxing.lingo.ui.base.PartialViewStateChange

data class WordDetailsFragmentViewState(
    val wordTitle: String = "",
    val wordDefinition: String = "",
    val wordExample: String = "",
    val favorite: Boolean = false
) {

    companion object {
        val INITIAL by lazyFast { WordDetailsFragmentViewState() }
    }

    class InitWordDetails(
        private val word: SearchResultModel
    ) : PartialViewStateChange<WordDetailsFragmentViewState> {
        override fun applyTo(viewState: WordDetailsFragmentViewState): WordDetailsFragmentViewState {
            return viewState.copy(
                wordTitle = word.word,
                wordDefinition = word.definition,
                wordExample = word.example,
                favorite = word.favorite
            )
        }
    }
}
