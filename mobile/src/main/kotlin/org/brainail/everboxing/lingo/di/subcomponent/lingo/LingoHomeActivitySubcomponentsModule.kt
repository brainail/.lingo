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
