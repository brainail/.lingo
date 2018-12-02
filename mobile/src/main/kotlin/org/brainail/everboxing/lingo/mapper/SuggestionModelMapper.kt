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

package org.brainail.everboxing.lingo.mapper

import org.brainail.everboxing.lingo.domain.model.Suggestion
import org.brainail.everboxing.lingo.model.SuggestionModel
import org.brainail.everboxing.lingo.util.ElasticSearchHighlighter
import javax.inject.Inject

class SuggestionModelMapper @Inject constructor(
    private val resultHighlighter: ElasticSearchHighlighter
) : Mapper<Suggestion, SuggestionModel> {

    override fun mapToModel(input: Suggestion): SuggestionModel {
        val highlightedWord: CharSequence = input.highlights?.let {
            resultHighlighter.makeHighlighted(input.word, it)
        } ?: input.word
        return SuggestionModel(highlightedWord, input.description, input.isRecent, input.id)
    }

    override fun mapFromModel(input: SuggestionModel): Suggestion {
        return Suggestion(input.word.toString(), input.description, input.isRecent, input.id)
    }
}
