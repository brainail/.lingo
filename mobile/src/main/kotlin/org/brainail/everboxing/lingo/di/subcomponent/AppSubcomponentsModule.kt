package org.brainail.everboxing.lingo.di.subcomponent

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import org.brainail.everboxing.lingo.di.ViewModelKey
import org.brainail.everboxing.lingo.di.scope.ActivityScope
import org.brainail.everboxing.lingo.di.subcomponent.lingo.LingoHomeActivityModule
import org.brainail.everboxing.lingo.di.subcomponent.lingo.LingoHomeActivitySubcomponentsModule
import org.brainail.everboxing.lingo.ui.home.LingoHomeActivity
import org.brainail.everboxing.lingo.ui.home.LingoHomeActivityViewModel

@Module
abstract class AppSubcomponentsModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [
        LingoHomeActivitySubcomponentsModule::class,
        LingoHomeActivityModule::class])
    abstract fun contributeLingoHomeActivityInjector(): LingoHomeActivity

    @Binds
    @IntoMap
    @ViewModelKey(LingoHomeActivityViewModel::class)
    abstract fun bindLingoHomeActivityViewModel(viewModel: LingoHomeActivityViewModel): ViewModel
}