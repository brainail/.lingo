package org.brainail.EverboxingLingo.di.view_model

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
internal abstract class ViewModelBuilder {
    @Binds
    internal abstract fun bindViewModelFactory(factory: LingoViewModelFactory): ViewModelProvider.Factory
}