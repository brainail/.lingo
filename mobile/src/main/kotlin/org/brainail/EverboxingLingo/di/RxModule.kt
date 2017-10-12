package org.brainail.EverboxingLingo.di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.brainail.EverboxingLingo.domain.executor.RxExecutor
import javax.inject.Named

@Module
class RxModule {
    @Provides
    @Named(MAIN_SCHEDULER)
    fun provideMainScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Named(BACKGROUND_SCHEDULER)
    fun provideBackgroundScheduler(): Scheduler = Schedulers.io()

    @Provides
    fun provideRxExecutor(
            @Named(MAIN_SCHEDULER) mainScheduler: Scheduler,
            @Named(BACKGROUND_SCHEDULER) backgroundScheduler: Scheduler)
            = RxExecutor(mainScheduler, backgroundScheduler)

    companion object {
        const val MAIN_SCHEDULER = "main_scheduler"
        const val BACKGROUND_SCHEDULER = "background_scheduler"
    }
}