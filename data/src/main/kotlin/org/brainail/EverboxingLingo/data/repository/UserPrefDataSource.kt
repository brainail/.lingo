package org.brainail.EverboxingLingo.data.repository

import org.brainail.EverboxingLingo.domain.model.UserProfile
import org.brainail.EverboxingLingo.domain.settings.UserSettings

internal class UserPrefDataSource(private val userSettings: UserSettings) {
    fun getUserProfile(): UserProfile? {
        val data: String? = userSettings.userProfile
        return if (data != null) UserProfile(data) else null
    }

    fun setUserProfile(data: UserProfile) {
        userSettings.userProfile = data.name
    }
}