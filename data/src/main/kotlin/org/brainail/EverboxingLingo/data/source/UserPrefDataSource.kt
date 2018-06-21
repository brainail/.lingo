package org.brainail.EverboxingLingo.data.source

import org.brainail.EverboxingLingo.domain.model.UserProfile
import org.brainail.EverboxingLingo.domain.settings.UserSettings
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