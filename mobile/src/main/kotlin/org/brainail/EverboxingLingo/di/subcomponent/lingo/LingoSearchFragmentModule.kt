package org.brainail.EverboxingLingo.di.subcomponent.lingo

import dagger.Module
import dagger.Provides
import org.brainail.EverboxingLingo.ui.search.LingoSearchPresenter
import org.brainail.EverboxingLingo.ui.search.LingoSearchPresenterImpl

@Module
class LingoSearchFragmentModule {
    @Provides
    fun provideProfilePresenter(): LingoSearchPresenter
            = LingoSearchPresenterImpl()
}