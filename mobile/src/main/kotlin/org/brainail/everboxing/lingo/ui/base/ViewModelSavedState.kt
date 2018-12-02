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

package org.brainail.everboxing.lingo.ui.base

import android.os.Bundle
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ViewModelSavedState(private val values: Bundle = Bundle()) : Parcelable {
    fun put(key: String, value: Any): ViewModelSavedState {
        when (value) {
            is String -> values.putString(key, value)
            is Int -> values.putInt(key, value)
            is Long -> values.putLong(key, value)
        }
        return this
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String): T? {
        return values.get(key) as T?
    }
}
