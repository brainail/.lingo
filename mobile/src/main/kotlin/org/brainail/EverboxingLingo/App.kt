package org.brainail.EverboxingLingo

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.ProcessLifecycleOwner
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import org.brainail.EverboxingLingo.di.AppComponent
import org.brainail.EverboxingLingo.di.AppModule
import org.brainail.EverboxingLingo.di.DaggerAppComponent
import org.brainail.EverboxingLingo.ui.AppLifecycleObserver
import org.brainail.logger.L
import javax.inject.Inject

class App: Application(), HasActivityInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Activity>

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = injector

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

}