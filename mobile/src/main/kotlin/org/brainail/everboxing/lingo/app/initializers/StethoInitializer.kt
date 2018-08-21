package org.brainail.everboxing.lingo.app.initializers

import android.app.Application
import com.facebook.stetho.Stetho
import org.brainail.everboxing.lingo.BuildConfig
import javax.inject.Inject

class StethoInitializer @Inject constructor(): AppInitializer {
    override fun init(application: Application) {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(application)
        }
    }
}