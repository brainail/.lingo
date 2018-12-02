package org.brainail.everboxing.lingo.di

import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import org.brainail.everboxing.lingo.di.subcomponent.AppSubcomponentsModule
import org.brainail.everboxing.lingo.util.log.AndroidLogTree
import org.brainail.logger.L
import javax.inject.Singleton

@Module(includes = [
    AndroidSupportInjectionModule::class,
    AppSubcomponentsModule::class,
    ViewModelModule::class])
class AndroidModule {
    @Provides
    @Singleton
    fun provideLogTree(): L.Tree = AndroidLogTree()
}
