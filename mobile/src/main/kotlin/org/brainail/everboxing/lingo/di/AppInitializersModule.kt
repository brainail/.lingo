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

package org.brainail.everboxing.lingo.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import org.brainail.everboxing.lingo.app.initializers.AppLifecycleInitializer
import org.brainail.everboxing.lingo.app.initializers.DataBindingInitializer
import org.brainail.everboxing.lingo.app.initializers.LoggerInitializer
import org.brainail.everboxing.lingo.app.initializers.StethoInitializer
import org.brainail.everboxing.lingo.base.app.AppInitializer
import org.brainail.everboxing.lingo.tasks.initializers.PreinstalledUrbanDataInitializer

@Module
abstract class AppInitializersModule {
    @Binds
    @IntoSet
    abstract fun bindLoggerInitializer(initializer: LoggerInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun bindAppLifecycleInitializer(initializer: AppLifecycleInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun bindStethoInitializer(initializer: StethoInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun bindDataBindingInitializer(initializer: DataBindingInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun bindPreinstalledUrbanDataInitializer(initializer: PreinstalledUrbanDataInitializer): AppInitializer
}
