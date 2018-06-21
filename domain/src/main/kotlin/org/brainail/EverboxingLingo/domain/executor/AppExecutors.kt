package org.brainail.EverboxingLingo.domain.executor

import io.reactivex.CompletableTransformer
import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.SingleTransformer

class AppExecutors(
        val mainScheduler: Scheduler,
        val backgroundScheduler: Scheduler) {

    fun <T> applyObservableSchedulers(): ObservableTransformer<T, T> = ObservableTransformer {
        it.subscribeOn(backgroundScheduler).observeOn(mainScheduler)
    }

    fun applyCompletableSchedulers(): CompletableTransformer = CompletableTransformer {
        it.subscribeOn(backgroundScheduler).observeOn(mainScheduler)
    }

    fun <T> applySingleSchedulers(): SingleTransformer<T, T> = SingleTransformer {
        it.subscribeOn(backgroundScheduler).observeOn(mainScheduler)
    }

    fun <T> applySingleBackgroundSchedulers(): SingleTransformer<T, T> = SingleTransformer {
        it.subscribeOn(backgroundScheduler).observeOn(backgroundScheduler)
    }

    fun <T> applyFlowableBackgroundSchedulers(): FlowableTransformer<T, T> = FlowableTransformer {
        it.subscribeOn(backgroundScheduler).observeOn(backgroundScheduler)
    }
}