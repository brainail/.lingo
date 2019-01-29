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

package org.brainail.everboxing.lingo.base.app

import android.app.Application

interface AppInitializer {
    fun init(application: Application)

    /**
     * Helper method to let someone be initialized before others.
     * For instance `logger` is one of the candidates.
     * By default the priority is equal to [Int.MIN_VALUE] and it's the lowest priority
     */
    fun priority() = Int.MIN_VALUE
}
