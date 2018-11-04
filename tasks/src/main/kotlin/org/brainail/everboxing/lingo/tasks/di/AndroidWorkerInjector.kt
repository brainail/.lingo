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
            throw RuntimeException("${application.javaClass.canonicalName} must " +
                    "implement ${HasWorkerInjector::class.java.canonicalName}")
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