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

package org.brainail.everboxing.lingo.tasks.di

import androidx.work.Worker
import dagger.MapKey
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.Multibinds
import kotlin.reflect.KClass

internal object AndroidWorkerInjector {
    fun inject(worker: Worker) {
        val application = worker.applicationContext
        if (application !is HasWorkerInjector) {
            throw RuntimeException(
                "${application.javaClass.canonicalName} must implement ${HasWorkerInjector::class.java.canonicalName}"
            )
        }

        val workerInjector = (application as HasWorkerInjector).workerInjector()
        workerInjector.inject(worker)
    }
}

interface HasWorkerInjector {
    fun workerInjector(): AndroidInjector<Worker>
}

@Module
abstract class AndroidWorkerInjectionModule {
    @Multibinds
    abstract fun workerInjectorFactories(): Map<String, AndroidInjector.Factory<out Worker>>
}

@MapKey
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class WorkerKey(val value: KClass<out Worker>)
