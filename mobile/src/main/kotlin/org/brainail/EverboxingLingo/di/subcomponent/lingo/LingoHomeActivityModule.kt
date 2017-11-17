package org.brainail.EverboxingLingo.di.subcomponent.lingo

import dagger.Module
import dagger.Provides
import org.brainail.EverboxingLingo.di.scope.ActivityScope
import org.brainail.EverboxingLingo.mapper.TextToSpeechResultMapper
import org.brainail.EverboxingLingo.ui.home.LingoHomeActivity
import org.brainail.EverboxingLingo.ui.home.LingoHomeActivityNavigator

@Module
class LingoHomeActivityModule {
    @Provides
    @ActivityScope
    fun provideLingoHomeActivityNavigator(activity: LingoHomeActivity): LingoHomeActivityNavigator
            = LingoHomeActivityNavigator(activity)

    @Provides
    @ActivityScope
    internal fun provideTextToSpeechResultMapper(): TextToSpeechResultMapper = TextToSpeechResultMapper()
}