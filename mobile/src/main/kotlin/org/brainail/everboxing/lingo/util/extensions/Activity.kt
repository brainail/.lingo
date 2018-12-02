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

@file:JvmName("ActivityUtils")

package org.brainail.everboxing.lingo.util.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.openFragment(
    fragmentTag: String,
    containerViewId: Int,
    openOnTop: Boolean = false,
    fragmentCreator: () -> Fragment
) {
    if (null == supportFragmentManager.findFragmentByTag(fragmentTag)) {
        supportFragmentManager.inTransaction {
            replace(containerViewId, fragmentCreator(), fragmentTag)
            if (openOnTop) {
                addToBackStack(fragmentTag)
            }
            this
        }
    }
}
