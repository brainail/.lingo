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

@file:JvmName("ViewModelUtils")

package org.brainail.everboxing.lingo.util.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import org.brainail.everboxing.lingo.util.SharedViewModel
import kotlin.reflect.KClass

inline fun <reified VM : ViewModel> FragmentActivity.getViewModel(provider: ViewModelProvider.Factory) =
    ViewModelProviders.of(this, provider).get(VM::class.viewModelProviderKey(), VM::class.java)

inline fun <reified VM : ViewModel> Fragment.getViewModel(provider: ViewModelProvider.Factory) =
    ViewModelProviders.of(this, provider).get(VM::class.viewModelProviderKey(), VM::class.java)

inline fun <reified VM : ViewModel> Fragment.getActivityViewModel(provider: ViewModelProvider.Factory) =
    ViewModelProviders.of(requireActivity(), provider).get(VM::class.viewModelProviderKey(), VM::class.java)

fun KClass<out ViewModel>.viewModelProviderKey(): String {
    val clazz = this.java
    val sharedViewModel = clazz.getAnnotation(SharedViewModel::class.java) ?: return clazz.canonicalName!!
    return sharedViewModel.klazz.java.canonicalName!! + sharedViewModel.key
}