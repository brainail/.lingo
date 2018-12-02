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

package org.brainail.everboxing.lingo.app.initializers

import android.app.Application
import org.brainail.everboxing.lingo.BuildConfig
import org.brainail.everboxing.lingo.base.app.AppInitializer
import org.brainail.logger.L
import javax.inject.Inject

class LoggerInitializer @Inject constructor(private val logTree: L.Tree) : AppInitializer {
    override fun init(application: Application) {
        if (BuildConfig.DEBUG) {
            L.plant(logTree)
        }
    }

    override fun priority() = Int.MAX_VALUE
}
