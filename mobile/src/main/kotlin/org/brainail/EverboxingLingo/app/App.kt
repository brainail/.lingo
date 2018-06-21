package org.brainail.EverboxingLingo.app

import android.content.Context
import android.support.multidex.MultiDex
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import org.brainail.EverboxingLingo.app.initializers.AppInitializers
import org.brainail.EverboxingLingo.di.DaggerAppComponent
import javax.inject.Inject

class App : DaggerApplication() {
    @Inject
    lateinit var appInitializers: AppInitializers

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        appInitializers.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}