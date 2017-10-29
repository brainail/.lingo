package org.brainail.EverboxingLingo.di

import dagger.Component
import dagger.android.AndroidInjector
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
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}