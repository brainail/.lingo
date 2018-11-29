package org.brainail.everboxing.lingo.data.settings

import android.content.SharedPreferences
import org.brainail.everboxing.lingo.data.settings.PrefDelegates.boolean
import org.brainail.everboxing.lingo.domain.ServiceType
import org.brainail.everboxing.lingo.domain.settings.SyncSettings

class SyncSettingsImpl(sharedPreferences: SharedPreferences) : SyncSettings {
    override var isPreinstalledUrbanDataInitialized by boolean(
            sharedPreferences, "preinstalled_${ServiceType.URBAN.key}_data_initialized", false)
}