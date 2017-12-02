package org.brainail.EverboxingLingo.ui.home

import org.brainail.EverboxingLingo.extensions.EMPTY_TEXT

/**
 * This file is part of Everboxing modules. <br/><br/>
 *
 * The MIT License (MIT) <br/><br/>
 *
 * Copyright (c) 2017 Malyshev Yegor aka brainail at org.brainail.everboxing@gmail.com <br/><br/>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy <br/>
 * of this software and associated documentation files (the "Software"), to deal <br/>
 * in the Software without restriction, including without limitation the rights <br/>
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell <br/>
 * copies of the Software, and to permit persons to whom the Software is <br/>
 * furnished to do so, subject to the following conditions: <br/><br/>
 *
 * The above copyright notice and this permission notice shall be included in <br/>
 * all copies or substantial portions of the Software. <br/><br/>
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR <br/>
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, <br/>
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE <br/>
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER <br/>
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, <br/>
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN <br/>
 * THE SOFTWARE.
 */
data class SearchViewState(
        val isInFocus: Boolean = false,
        val displayedText: String = EMPTY_TEXT,
        val isClearAvailable: Boolean = false,
        val cursorPosition: CursorPosition = CursorPosition.KEEP,
        val isTextToSpeechAvailable: Boolean = false,
        val isLogoDisplayed: Boolean = false,
        private val isLoadingSuggestions: Boolean = false,
        val displayedSuggestions: List<String> = emptyList()) {

    val displayLoading = isInFocus && isLoadingSuggestions

    companion object {
        val INITIAL by lazy {
            SearchViewState(
                    isInFocus = false,
                    isLogoDisplayed = true,
                    displayedText = EMPTY_TEXT,
                    isClearAvailable = false,
                    isTextToSpeechAvailable = true,
                    cursorPosition = CursorPosition.KEEP,
                    isLoadingSuggestions = false)
        }
    }

    enum class CursorPosition {
        /**
         * Move to the end
         */
        END,
        /**
         * Keep it as it is
         */
        KEEP
    }
}