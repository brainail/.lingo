package org.brainail.everboxing.lingo.di.subcomponent.lingo

import dagger.Module
import dagger.Provides
import org.brainail.everboxing.lingo.di.scope.FragmentScope
import org.brainail.everboxing.lingo.ui.home.LingoHomeActivity
import org.brainail.everboxing.lingo.ui.home.explore.ExploreFragmentNavigator

@Module
class LingoSearchFragmentModule {
    @Provides
    @FragmentScope
    fun provideLingoSearchFragmentNavigator(activity: LingoHomeActivity) = ExploreFragmentNavigator(activity)
}
