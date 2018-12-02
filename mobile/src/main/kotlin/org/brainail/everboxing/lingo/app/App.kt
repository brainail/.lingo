package org.brainail.everboxing.lingo.app

import androidx.work.Worker
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerApplication
import org.brainail.everboxing.lingo.app.initializers.AppInitializers
import org.brainail.everboxing.lingo.di.DaggerAppComponent
import org.brainail.everboxing.lingo.tasks.di.HasWorkerInjector
import javax.inject.Inject

class App : DaggerApplication(), HasWorkerInjector {
    @Inject
    lateinit var workerInjector: DispatchingAndroidInjector<Worker>
    @Inject
    lateinit var appInitializers: AppInitializers

    override fun onCreate() {
        super.onCreate()
        appInitializers.init(this)
    }

    override fun workerInjector() = workerInjector

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}
