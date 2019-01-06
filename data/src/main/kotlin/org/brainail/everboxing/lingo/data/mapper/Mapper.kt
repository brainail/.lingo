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

package org.brainail.everboxing.lingo.data.mapper

interface Mapper<F, T> {
    /**
     * Maps __FROM__ to __TO__
     */
    fun mapF(input: F): T = throw UnsupportedOperationException()

    /**
     * Maps __TO__ to __FROM__
     */
    fun mapT(input: T): F = throw UnsupportedOperationException()
}
