package org.brainail.EverboxingLingo.ui

import android.os.Bundle
import javax.inject.Inject

open class MvpFragment<V, VS, P : Presenter<V, VS>> : BaseFragment() {

    @Inject
    protected lateinit var presenter: P

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.attachView(getMvpView(), getViewStateFromBundle(savedInstanceState))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveViewStateToBundle(outState, presenter.viewStateToSave)
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    protected open fun getViewStateFromBundle(bundle: Bundle?): VS? {
        return null
    }

    protected open fun saveViewStateToBundle(bundle: Bundle, state: VS?) {
        // do nothing
    }

    protected open fun getMvpView(): V {
        @Suppress("UNCHECKED_CAST")
        return this as V
    }
}