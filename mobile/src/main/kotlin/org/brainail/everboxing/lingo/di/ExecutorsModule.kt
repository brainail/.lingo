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

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.brainail.everboxing.lingo.domain.executor.AppExecutors
import javax.inject.Named
import javax.inject.Singleton

@Module
class ExecutorsModule {
    @Provides
    @Named(MAIN_SCHEDULER)
    @Singleton
    fun provideMainScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Named(BACKGROUND_SCHEDULER)
    @Singleton
    fun provideBackgroundScheduler(): Scheduler = Schedulers.io()

    @Provides
    @Singleton
    fun provideAppExecutors(
        @Named(MAIN_SCHEDULER) mainScheduler: Scheduler,
        @Named(BACKGROUND_SCHEDULER) backgroundScheduler: Scheduler
    ) = AppExecutors(mainScheduler, backgroundScheduler)

    companion object {
        const val MAIN_SCHEDULER = "main_scheduler"
        const val BACKGROUND_SCHEDULER = "background_scheduler"
    }
}
