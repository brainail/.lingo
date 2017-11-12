package org.brainail.EverboxingLingo.di.subcomponent.lingo

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.brainail.EverboxingLingo.ui.home.explore.LingoSearchFragment

@Module
abstract class LingoHomeActivitySubcomponentsModule {
    @ContributesAndroidInjector(modules = arrayOf(LingoSearchFragmentModule::class))
    abstract fun contributeLingoSearchFragmentInjector(): LingoSearchFragment
}