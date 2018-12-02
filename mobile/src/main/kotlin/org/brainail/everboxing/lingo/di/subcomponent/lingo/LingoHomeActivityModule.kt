package org.brainail.everboxing.lingo.di.subcomponent.lingo

import dagger.Module
import dagger.Provides
import org.brainail.everboxing.lingo.di.scope.ActivityScope
import org.brainail.everboxing.lingo.mapper.TextToSpeechResultMapper
import org.brainail.everboxing.lingo.ui.home.LingoHomeActivity
import org.brainail.everboxing.lingo.ui.home.LingoHomeActivityActor
import org.brainail.everboxing.lingo.ui.home.LingoHomeActivityNavigator

@Module
class LingoHomeActivityModule {
    @Provides
    @ActivityScope
    fun provideLingoHomeActivityNavigator(activity: LingoHomeActivity) = LingoHomeActivityNavigator(activity)

    @Provides
    @ActivityScope
    fun provideLingoHomeActivityActor(activity: LingoHomeActivity) = LingoHomeActivityActor(activity)

    @Provides
    @ActivityScope
    fun provideTextToSpeechResultMapper() = TextToSpeechResultMapper()
}
