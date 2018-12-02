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

package org.brainail.everboxing.lingo.ui.base

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

abstract class RxAwareViewModel : BaseViewModel() {
    private val disposables = CompositeDisposable()

    protected fun <T> bindObservable(observable: Observable<T>): Observable<T> {
        return observable.doOnSubscribe {
            disposables.add(it)
        }
    }

    protected fun <T> bindSingle(single: Single<T>): Single<T> {
        return single.doOnSubscribe {
            disposables.add(it)
        }
    }

    protected fun <T> bindMaybe(maybe: Maybe<T>): Maybe<T> {
        return maybe.doOnSubscribe {
            disposables.add(it)
        }
    }

    protected fun bindCompletable(completable: Completable): Completable {
        return completable.doOnSubscribe {
            disposables.add(it)
        }
    }

    override fun clear() {
        super.clear()
        disposables.clear()
    }
}
