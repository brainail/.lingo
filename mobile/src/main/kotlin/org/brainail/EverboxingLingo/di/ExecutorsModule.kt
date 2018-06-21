package org.brainail.EverboxingLingo.di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.brainail.EverboxingLingo.domain.executor.AppExecutors
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
            @Named(BACKGROUND_SCHEDULER) backgroundScheduler: Scheduler) =
            AppExecutors(mainScheduler, backgroundScheduler)

    companion object {
        const val MAIN_SCHEDULER = "main_scheduler"
        const val BACKGROUND_SCHEDULER = "background_scheduler"
    }
}