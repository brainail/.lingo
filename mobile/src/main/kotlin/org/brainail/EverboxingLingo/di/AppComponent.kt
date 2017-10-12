package org.brainail.EverboxingLingo.di

import dagger.Component
import org.brainail.EverboxingLingo.App
import org.brainail.EverboxingLingo.data.di.module.DataModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AndroidModule::class,
        AppModule::class,
        RxModule::class,
        DataModule::class
))
interface AppComponent {
    fun inject(app: App)
}