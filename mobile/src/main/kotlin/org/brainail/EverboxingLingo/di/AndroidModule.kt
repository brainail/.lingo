package org.brainail.EverboxingLingo.di

import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule
import org.brainail.EverboxingLingo.di.subcomponent.LingoHomeSubcomponentsModule
import org.brainail.EverboxingLingo.di.view_model.ViewModelBuilder

@Module(includes = arrayOf(
        AndroidSupportInjectionModule::class,
        LingoHomeSubcomponentsModule::class,
        ViewModelBuilder::class
))
class AndroidModule