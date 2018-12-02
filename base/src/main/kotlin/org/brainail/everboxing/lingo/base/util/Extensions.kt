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

package org.brainail.everboxing.lingo.base.util

/**
 * Implementation of lazy that is not thread safe.
 */
fun <T> lazyFast(operation: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) {
    operation()
}

/**
 * Force a when statement to check all options.
 */
val <T> T.checkAllMatched: T
    get() = this

inline fun consume(f: () -> Unit): Boolean {
    f()
    return true
}

inline fun safeTry(block: () -> Unit) {
    try {
        block()
    } catch (ignore: Throwable) {
        // do nothing
    }
}

@Suppress("TooGenericExceptionCaught")
inline fun <T> tryOrRuntime(block: () -> T): T {
    return try {
        block()
    } catch (cause: Throwable) {
        throw RuntimeException(cause)
    }
}
