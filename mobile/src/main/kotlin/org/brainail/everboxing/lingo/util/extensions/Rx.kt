@file:JvmName("RxUtils")

package org.brainail.everboxing.lingo.util.extensions;

import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

private const val DEFAULT_SEAMLESS_LOADING_EFFECT_TIME_MILLIS = 1000L

fun <T> Flowable<T>.seamlessLoading(): Flowable<T> {
    return Flowable.combineLatest(
            this,
            Flowable.timer(DEFAULT_SEAMLESS_LOADING_EFFECT_TIME_MILLIS, TimeUnit.MILLISECONDS),
            BiFunction { t, _ -> t })
}
