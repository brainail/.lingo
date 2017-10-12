package org.brainail.EverboxingLingo.ui

import android.os.Bundle
import javax.inject.Inject

abstract class MvpActivity<V, VS, P : Presenter<V, VS>> : BaseActivity() {

    @Inject
    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(getMvpView(), getViewStateFromBundle(savedInstanceState))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveViewStateToBundle(outState, presenter.viewStateToSave)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
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