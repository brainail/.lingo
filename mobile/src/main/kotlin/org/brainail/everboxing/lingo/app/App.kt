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

package org.brainail.everboxing.lingo.app

import androidx.work.Worker
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerApplication
import org.brainail.everboxing.lingo.app.initializers.AppInitializers
import org.brainail.everboxing.lingo.di.DaggerAppComponent
import org.brainail.everboxing.lingo.tasks.di.HasWorkerInjector
import javax.inject.Inject

class App : DaggerApplication(), HasWorkerInjector {
    @Inject
    lateinit var workerInjector: DispatchingAndroidInjector<Worker>
    @Inject
    lateinit var appInitializers: AppInitializers

    override fun onCreate() {
        super.onCreate()
        appInitializers.init(this)
    }

    override fun workerInjector() = workerInjector

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}
