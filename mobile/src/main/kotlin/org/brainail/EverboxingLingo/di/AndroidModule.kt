package org.brainail.EverboxingLingo.di

import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule
import org.brainail.EverboxingLingo.di.subcomponent.AppSubcomponentsModule
import org.brainail.EverboxingLingo.di.view_model.ViewModelBuilder

@Module(includes = arrayOf(
        AndroidSupportInjectionModule::class,
        AppSubcomponentsModule::class,
        ViewModelBuilder::class
))
class AndroidModule