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

@file:JvmName("WidgetUtils")

package org.brainail.everboxing.lingo.util.extensions

import android.text.Editable
import org.brainail.everboxing.lingo.util.TextWatcherAdapter
import org.brainail.flysearch.FloatingSearchView

inline fun FloatingSearchView.addAfterTextChangedListener(crossinline action: (Editable) -> Unit) {
    addTextChangedListener(object : TextWatcherAdapter() {
        override fun afterTextChanged(query: Editable) {
            action(query)
        }
    })
}
