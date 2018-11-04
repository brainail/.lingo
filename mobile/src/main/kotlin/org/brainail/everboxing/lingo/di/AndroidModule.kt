package org.brainail.everboxing.lingo.di

import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule
import org.brainail.everboxing.lingo.di.subcomponent.AppSubcomponentsModule
import org.brainail.everboxing.lingo.di.view_model.ViewModelBuilder

@Module(includes = [
    AndroidSupportInjectionModule::class,
    AppSubcomponentsModule::class,
    ViewModelBuilder::class])
class AndroidModule