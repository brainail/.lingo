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

package org.brainail.everboxing.lingo.data.settings

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import kotlin.reflect.KProperty
import kotlin.properties.ReadWriteProperty as RWP

private class PrefDelegate<T>(
    private val prefs: SharedPreferences,
    private val defaultValue: T,
    private val getter: SharedPreferences.(String, T) -> T,
    private val setter: Editor.(String, T) -> Editor,
    private val key: String
) : RWP<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>) = prefs.getter(key, defaultValue)
    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) = prefs.edit().setter(key, value).apply()
}

object PrefDelegates {
    fun string(prefs: SharedPreferences, key: String, defaultValue: String): RWP<Any, String> =
        PrefDelegate(prefs, defaultValue, SharedPreferences::getString, Editor::putString, key)

    fun nullableString(prefs: SharedPreferences, key: String, defaultValue: String?): RWP<Any, String?> =
        PrefDelegate(prefs, defaultValue, SharedPreferences::getString, Editor::putString, key)

    fun set(prefs: SharedPreferences, key: String, defaultValue: Set<String>): RWP<Any, Set<String>> =
        PrefDelegate(prefs, defaultValue, SharedPreferences::getStringSet, Editor::putStringSet, key)

    fun nullableSet(prefs: SharedPreferences, key: String, defaultValue: Set<String>?): RWP<Any, Set<String>?> =
        PrefDelegate(prefs, defaultValue, SharedPreferences::getStringSet, Editor::putStringSet, key)

    fun int(prefs: SharedPreferences, key: String, defaultValue: Int): RWP<Any, Int> =
        PrefDelegate(prefs, defaultValue, SharedPreferences::getInt, Editor::putInt, key)

    fun boolean(prefs: SharedPreferences, key: String, defaultValue: Boolean): RWP<Any, Boolean> =
        PrefDelegate(prefs, defaultValue, SharedPreferences::getBoolean, Editor::putBoolean, key)

    fun long(prefs: SharedPreferences, key: String, defaultValue: Long): RWP<Any, Long> =
        PrefDelegate(prefs, defaultValue, SharedPreferences::getLong, Editor::putLong, key)

    fun float(prefs: SharedPreferences, key: String, defaultValue: Float): RWP<Any, Float> =
        PrefDelegate(prefs, defaultValue, SharedPreferences::getFloat, Editor::putFloat, key)
}
