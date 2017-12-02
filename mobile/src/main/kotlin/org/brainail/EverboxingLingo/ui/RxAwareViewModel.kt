package org.brainail.EverboxingLingo.ui

import io.reactivex.disposables.CompositeDisposable

open class RxAwareViewModel : BaseViewModel() {

    val disposables = CompositeDisposable()

    override fun clear() {
        super.clear()
        disposables.clear()
    }

}