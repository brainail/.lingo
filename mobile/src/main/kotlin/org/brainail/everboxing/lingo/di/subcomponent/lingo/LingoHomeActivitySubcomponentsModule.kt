package org.brainail.everboxing.lingo.di.subcomponent.lingo

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import org.brainail.everboxing.lingo.di.scope.FragmentScope
import org.brainail.everboxing.lingo.di.ViewModelKey
import org.brainail.everboxing.lingo.ui.home.details.WordDetailsFragment
import org.brainail.everboxing.lingo.ui.home.explore.ExploreFragment
import org.brainail.everboxing.lingo.ui.home.explore.ExploreFragmentViewModel

@Module
abstract class LingoHomeActivitySubcomponentsModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = [LingoSearchFragmentModule::class])
    abstract fun contributeLingoSearchFragmentInjector(): ExploreFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeLingoSearchResultDetailsFragmentInjector(): WordDetailsFragment

    @Binds
    @IntoMap
    @ViewModelKey(ExploreFragmentViewModel::class)
    abstract fun bindLingoSearchFragmentViewModel(viewModel: ExploreFragmentViewModel): ViewModel
}