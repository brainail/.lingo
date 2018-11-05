package org.brainail.everboxing.lingo.di

import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule
import org.brainail.everboxing.lingo.di.subcomponent.AppSubcomponentsModule

@Module(includes = [
    AndroidSupportInjectionModule::class,
    AppSubcomponentsModule::class,
    ViewModelModule::class])
class AndroidModule