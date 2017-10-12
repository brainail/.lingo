package org.brainail.EverboxingLingo.ui

/**
 * The base interface for each mvp presenter.
 *
 * @param V The type of the View this presenter responds to
 * @param VS The type of the View's State (Model)
 */
interface Presenter<V, VS> {

    val viewStateToSave: VS?

    fun attachView(view: V, viewState: VS?)

    fun detachView()
}