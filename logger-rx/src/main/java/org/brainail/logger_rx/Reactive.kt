package org.brainail.logger_rx

import io.reactivex.Single
import org.brainail.logger.L

inline fun <reified T> Single<T>.logEvents(): Single<T> {
    return doOnEvent { response, throwable ->
        L.v("doOnEvent: response = $response, throwable = $throwable")
    }
}
