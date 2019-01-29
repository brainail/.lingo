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

package org.brainail.everboxing.lingo.base.file

import java.io.InputStream

interface FileStore {
    /**
     * Returns [InputStream] based on the file that comes with the application.
     * The location can differ due to app type. In Android it will be **assets** folder.
     *
     * @param path the relative path to the file
     */
    fun openAsset(path: String): InputStream
}