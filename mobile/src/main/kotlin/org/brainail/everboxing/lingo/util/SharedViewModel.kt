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

import androidx.lifecycle.ViewModel
import kotlin.reflect.KClass

/**
 * [klazz] Shared class
 *
 * [key] Extra key in order to make a difference
 *
 * The purposes of this annotation is to give some extra information about
 * a possible shared class between [android.app.Activity] and [androidx.fragment.app.Fragment]
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class SharedViewModel(val klazz: KClass<out ViewModel>, val key: String = "")
