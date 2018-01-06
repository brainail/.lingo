package org.brainail.EverboxingLingo.di.subcomponent.lingo

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import org.brainail.EverboxingLingo.di.view_model.ViewModelKey
import org.brainail.EverboxingLingo.ui.home.explore.LingoSearchFragment
import org.brainail.EverboxingLingo.ui.home.explore.LingoSearchFragmentViewModel

@Module
abstract class LingoHomeActivitySubcomponentsModule {
    @ContributesAndroidInjector(modules = arrayOf(LingoSearchFragmentModule::class))
    abstract fun contributeLingoSearchFragmentInjector(): LingoSearchFragment

    @Binds
    @IntoMap
    @ViewModelKey(LingoSearchFragmentViewModel::class)
    abstract fun bindLingoHomeActivityViewModel(viewModel: LingoSearchFragmentViewModel): ViewModel
}