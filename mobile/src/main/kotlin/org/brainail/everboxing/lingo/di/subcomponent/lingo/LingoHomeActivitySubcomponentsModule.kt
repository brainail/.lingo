/*
 * Copyright 2018 Malyshev Yegor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brainail.everboxing.lingo.di.subcomponent.lingo

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import org.brainail.everboxing.lingo.di.ViewModelKey
import org.brainail.everboxing.lingo.di.scope.FragmentScope
import org.brainail.everboxing.lingo.ui.home.details.WordDetailsFragment
import org.brainail.everboxing.lingo.ui.home.details.WordDetailsFragmentViewModel
import org.brainail.everboxing.lingo.ui.home.explore.ExploreFragment
import org.brainail.everboxing.lingo.ui.home.explore.ExploreFragmentViewModel
import org.brainail.everboxing.lingo.ui.home.favorite.FavoriteFragment
import org.brainail.everboxing.lingo.ui.home.favorite.FavoriteFragmentViewModel
import org.brainail.everboxing.lingo.ui.home.history.HistoryFragment
import org.brainail.everboxing.lingo.ui.home.history.HistoryFragmentViewModel

@Module
abstract class LingoHomeActivitySubcomponentsModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = [LingoExploreFragmentModule::class])
    abstract fun contributeExploreFragmentInjector(): ExploreFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [LingoFavoriteFragmentModule::class])
    abstract fun contributeFavoriteFragmentInjector(): FavoriteFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [LingoHistoryFragmentModule::class])
    abstract fun contributeHistoryFragmentInjector(): HistoryFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeWordDetailsFragmentInjector(): WordDetailsFragment

    @Binds
    @IntoMap
    @ViewModelKey(ExploreFragmentViewModel::class)
    abstract fun bindLingoExploreFragmentViewModel(viewModel: ExploreFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteFragmentViewModel::class)
    abstract fun bindLingoFavoriteFragmentViewModel(viewModel: FavoriteFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HistoryFragmentViewModel::class)
    abstract fun bindLingoHistoryFragmentViewModel(viewModel: HistoryFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WordDetailsFragmentViewModel::class)
    abstract fun bindWordDetailsFragmentViewModel(viewModel: WordDetailsFragmentViewModel): ViewModel
}
