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