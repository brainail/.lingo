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

package org.brainail.everboxing.lingo.data.source

import org.brainail.everboxing.lingo.domain.model.UserProfile
import org.brainail.everboxing.lingo.domain.settings.UserSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPrefDataSource @Inject constructor(private val userSettings: UserSettings) {
    fun getUserProfile(): UserProfile? {
        val data: String? = userSettings.userProfile
        return if (data != null) UserProfile(data) else null
    }

    fun setUserProfile(data: UserProfile) {
        userSettings.userProfile = data.name
    }
}
