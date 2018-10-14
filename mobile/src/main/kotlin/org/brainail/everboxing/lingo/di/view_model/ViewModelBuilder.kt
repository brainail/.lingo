package org.brainail.everboxing.lingo.di.view_model

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
internal abstract class ViewModelBuilder {
    @Binds
    internal abstract fun bindViewModelFactory(factory: LingoViewModelFactory): ViewModelProvider.Factory
}