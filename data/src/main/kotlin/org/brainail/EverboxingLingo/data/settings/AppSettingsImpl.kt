package org.brainail.EverboxingLingo.data.settings

import android.content.SharedPreferences
import org.brainail.EverboxingLingo.domain.settings.AppSettings

internal class AppSettingsImpl(prefs: android.content.SharedPreferences) : AppSettings, SharedPreferences by prefs {
    override var userProfile: String? by PrefDelegates.stringPref("user_profile", null)
}