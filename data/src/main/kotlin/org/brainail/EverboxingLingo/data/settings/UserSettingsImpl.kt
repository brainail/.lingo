package org.brainail.EverboxingLingo.data.settings

import android.content.SharedPreferences
import org.brainail.EverboxingLingo.domain.settings.UserSettings

internal class UserSettingsImpl(sharedPreferences: SharedPreferences) : UserSettings {
    override var userProfile: String? by PrefDelegates.nullableString(sharedPreferences, "user_profile", null)
}