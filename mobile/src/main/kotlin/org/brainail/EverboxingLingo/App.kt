package org.brainail.EverboxingLingo

import android.arch.lifecycle.ProcessLifecycleOwner
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import org.brainail.EverboxingLingo.di.DaggerAppComponent
import org.brainail.EverboxingLingo.ui.AppLifecycleObserver
import org.brainail.logger.L
import javax.inject.Inject

class App: DaggerApplication() {

    @Inject
    fun configureLogging(logTree: L.Tree) {
        if (BuildConfig.DEBUG) {
            L.plant(logTree)
        }
    }

    @Inject
    fun registerAppLifecycleObserver(observer: AppLifecycleObserver) {
        ProcessLifecycleOwner.get().lifecycle.addObserver(observer)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

}