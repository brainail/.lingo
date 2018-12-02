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

package org.brainail.everboxing.lingo.domain.executor

import io.reactivex.CompletableTransformer
import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.SingleTransformer

class AppExecutors(
    val mainScheduler: Scheduler,
    val backgroundScheduler: Scheduler
) {

    fun <T> applyObservableSchedulers(): ObservableTransformer<T, T> = ObservableTransformer {
        it.subscribeOn(backgroundScheduler).observeOn(mainScheduler)
    }

    fun applyCompletableSchedulers(): CompletableTransformer = CompletableTransformer {
        it.subscribeOn(backgroundScheduler).observeOn(mainScheduler)
    }

    fun applyCompletableBackgroundSchedulers(): CompletableTransformer = CompletableTransformer {
        it.subscribeOn(backgroundScheduler)
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
