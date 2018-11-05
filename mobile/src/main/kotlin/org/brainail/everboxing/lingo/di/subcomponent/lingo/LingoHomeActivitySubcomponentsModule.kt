package org.brainail.everboxing.lingo.di.subcomponent.lingo

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import org.brainail.everboxing.lingo.di.scope.FragmentScope
import org.brainail.everboxing.lingo.di.ViewModelKey
import org.brainail.everboxing.lingo.ui.home.details.LingoSearchResultDetailsFragment
import org.brainail.everboxing.lingo.ui.home.explore.LingoSearchFragment
import org.brainail.everboxing.lingo.ui.home.explore.LingoSearchFragmentViewModel

@Module
abstract class LingoHomeActivitySubcomponentsModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = [LingoSearchFragmentModule::class])
    abstract fun contributeLingoSearchFragmentInjector(): LingoSearchFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeLingoSearchResultDetailsFragmentInjector(): LingoSearchResultDetailsFragment

    @Binds
    @IntoMap
    @ViewModelKey(LingoSearchFragmentViewModel::class)
    abstract fun bindLingoSearchFragmentViewModel(viewModel: LingoSearchFragmentViewModel): ViewModel
}