package org.brainail.EverboxingLingo.di

import dagger.Component
import dagger.android.AndroidInjector
import org.brainail.EverboxingLingo.app.App
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AndroidModule::class,
        AppModule::class,
        ExecutorsModule::class,
        DataModule::class
))
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}