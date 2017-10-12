package org.brainail.EverboxingLingo.ui

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

abstract class RxBasePresenter<V, VS> : BasePresenter<V, VS>() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun <T> bindObservable(observable: Observable<T>): Observable<T> {
        return observable.doOnSubscribe {
            compositeDisposable.add(it)
        }
    }

    protected fun <T> bindSingle(single: Single<T>): Single<T> {
        return single.doOnSubscribe {
            compositeDisposable.add(it)
        }
    }

    protected fun <T> bindMaybe(maybe: Maybe<T>): Maybe<T> {
        return maybe.doOnSubscribe {
            compositeDisposable.add(it)
        }
    }

    protected fun bindCompletable(completable: Completable): Completable {
        return completable.doOnSubscribe {
            compositeDisposable.add(it)
        }
    }

    override fun onViewDetached() {
        super.onViewDetached()
        compositeDisposable.clear()
    }
}