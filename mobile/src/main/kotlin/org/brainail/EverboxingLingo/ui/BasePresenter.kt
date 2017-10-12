package org.brainail.EverboxingLingo.ui

abstract class BasePresenter<V, VS> : Presenter<V, VS> {

    protected var view: V? = null

    protected var viewState: VS? = null

    override val viewStateToSave: VS?
        get() = viewState

    final override fun attachView(view: V, viewState: VS?) {
        this.view = view
        updateViewState(obtainViewStateOnAttach(viewState), true)
        onViewAttached(view, this.viewState)
    }

    protected open fun obtainViewStateOnAttach(restoredViewState: VS?): VS? {
        return restoredViewState ?: viewState ?: createInitialViewState()
    }

    /**
     * Sub classes should call this method whenever vies state is changed.
     */
    protected fun updateViewState(viewState: VS?) {
        updateViewState(viewState, false)
    }

    private fun updateViewState(viewState: VS?, newViewAttached: Boolean) {
        this.viewState = viewState
        val viewRef: V? = view
        if (viewRef != null && viewState != null) {
            applyViewState(viewRef, viewState, newViewAttached)
        }
    }

    final override fun detachView() {
        view = null
        onViewDetached()
    }

    protected open fun createInitialViewState(): VS? {
        return null
    }

    /**
     * Called just after view is attached to presenter.
     */
    protected open fun onViewAttached(view: V, viewState: VS?) {
        // do nothing by default
    }

    /**
     * Sub classes should not call this method directly, use [updateViewState] instead
     * to update view state.
     * This method should be used to render view state to view.
     */
    protected abstract fun applyViewState(view: V, state: VS, newViewAttached: Boolean)

    /**
     * Called just after view is detached from presenter.
     */
    protected open fun onViewDetached() {
        // do noting by default
    }
}