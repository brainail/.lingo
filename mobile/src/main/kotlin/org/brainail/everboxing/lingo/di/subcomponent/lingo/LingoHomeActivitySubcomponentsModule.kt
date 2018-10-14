package org.brainail.everboxing.lingo.di.subcomponent.lingo

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import org.brainail.everboxing.lingo.di.view_model.ViewModelKey
import org.brainail.everboxing.lingo.ui.home.explore.LingoSearchFragment
import org.brainail.everboxing.lingo.ui.home.explore.LingoSearchFragmentViewModel

@Module
abstract class LingoHomeActivitySubcomponentsModule {
    @ContributesAndroidInjector(modules = arrayOf(LingoSearchFragmentModule::class))
    abstract fun contributeLingoSearchFragmentInjector(): LingoSearchFragment

    @Binds
    @IntoMap
    @ViewModelKey(LingoSearchFragmentViewModel::class)
    abstract fun bindLingoSearchFragmentViewModel(viewModel: LingoSearchFragmentViewModel): ViewModel
}