package org.brainail.EverboxingLingo.di.subcomponent

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import org.brainail.EverboxingLingo.di.scope.ActivityScope
import org.brainail.EverboxingLingo.di.subcomponent.lingo.LingoHomeActivityModule
import org.brainail.EverboxingLingo.di.subcomponent.lingo.LingoHomeActivitySubcomponentsModule
import org.brainail.EverboxingLingo.di.view_model.ViewModelKey
import org.brainail.EverboxingLingo.ui.home.LingoHomeActivity
import org.brainail.EverboxingLingo.ui.home.LingoHomeActivityViewModel

@Module
abstract class LingoHomeSubcomponentsModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(
            LingoHomeActivitySubcomponentsModule::class,
            LingoHomeActivityModule::class))
    abstract fun contributeLingoHomeActivityInjector(): LingoHomeActivity

    @Binds
    @IntoMap
    @ViewModelKey(LingoHomeActivityViewModel::class)
    abstract fun bindLingoHomeActivityViewModel(viewModel: LingoHomeActivityViewModel): ViewModel
}