package org.brainail.EverboxingLingo.data.repository

import org.brainail.EverboxingLingo.domain.model.UserProfile
import org.brainail.EverboxingLingo.domain.settings.AppSettings

internal class UserPrefDataSource(private val appSettings: AppSettings) {
    fun getUserProfile(): UserProfile? {
        val data: String? = appSettings.userProfile
        return if (data != null) UserProfile(data) else null
    }

    fun setUserProfile(data: UserProfile) {
        appSettings.userProfile = data.name
    }
}