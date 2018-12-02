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

package org.brainail.everboxing.lingo.util

import android.content.Context
import android.text.style.ForegroundColorSpan
import androidx.annotation.WorkerThread
import androidx.core.content.ContextCompat
import androidx.core.text.set
import androidx.core.text.toSpannable
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.base.util.lazyFast
import org.brainail.everboxing.lingo.util.extensions.compareCaselessTo
import javax.inject.Inject

/**
 * Home-made highlighter based on __LCS__.
 */
class ElasticSearchHighlighter @Inject constructor(private val context: Context) {

    private val highlightColor: Int by lazyFast {
        ContextCompat.getColor(context, R.color.elastic_search_highlighter)
    }

    /**
     * @param text Text to highlight
     * @param word What parts should be highlighted in [text]
     *
     * @return Highlighted text using special markups
     */
    @Suppress("ComplexMethod")
    @WorkerThread
    fun makeHighlighted(text: String, word: String): CharSequence {
        val textLength = text.length
        val wordLength = word.length
        val highlights = Array(textLength + 1) { IntArray(wordLength + 1) }

        for (i in 1..textLength) {
            for (j in 1..wordLength) {
                when {
                    text[i - 1].compareCaselessTo(word[j - 1]) -> highlights[i][j] = highlights[i - 1][j - 1] + 1
                    else -> highlights[i][j] = Math.max(highlights[i - 1][j], highlights[i][j - 1])
                }
            }
        }

        var index = highlights[textLength][wordLength]
        val bestMatch = IntArray(index)
        var textIndex = textLength
        var wordIndex = wordLength

        while (textIndex > 0 && wordIndex > 0) {
            when {
                highlights[textIndex - 1][wordIndex] == highlights[textIndex][wordIndex] -> textIndex--
                text[textIndex - 1].compareCaselessTo(word[wordIndex - 1]) -> {
                    bestMatch[index - 1] = textIndex - 1
                    textIndex--
                    wordIndex--
                    index--
                }
                highlights[textIndex - 1][wordIndex] > highlights[textIndex][wordIndex - 1] -> textIndex--
                else -> wordIndex--
            }
        }

        val result = text.toSpannable()
        var leftIndex = 0
        while (leftIndex < bestMatch.size) {
            var rightIndex = leftIndex
            while (rightIndex + 1 < bestMatch.size &&
                bestMatch[rightIndex] + 1 == bestMatch[rightIndex + 1]
            ) ++rightIndex
            result[bestMatch[leftIndex], bestMatch[rightIndex] + 1] = ForegroundColorSpan(highlightColor)
            leftIndex = rightIndex + 1
        }

        return result
    }
}
