package org.brainail.everboxing.lingo.app

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import org.brainail.everboxing.lingo.app.initializers.AppInitializers
import org.brainail.everboxing.lingo.di.DaggerAppComponent
import javax.inject.Inject

class App : DaggerApplication() {
    @Inject
    lateinit var appInitializers: AppInitializers

    override fun onCreate() {
        super.onCreate()
        appInitializers.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}