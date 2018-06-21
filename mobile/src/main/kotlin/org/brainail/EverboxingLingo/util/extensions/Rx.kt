package org.brainail.EverboxingLingo.util.extensions;

import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

fun <T> Flowable<T>.seamlessLoading(): Flowable<T> {
    return Flowable.combineLatest(
            this,
            Flowable.timer(1000, TimeUnit.MILLISECONDS),
            BiFunction { t, _ -> t })
}
