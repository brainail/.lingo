package org.brainail.everboxing.lingo.data.settings

import android.content.SharedPreferences
import org.brainail.everboxing.lingo.data.settings.PrefDelegates.nullableString
import org.brainail.everboxing.lingo.domain.settings.UserSettings

class UserSettingsImpl(sharedPreferences: SharedPreferences) : UserSettings {
    override var userProfile: String? by nullableString(sharedPreferences, "user_profile", null)
}
