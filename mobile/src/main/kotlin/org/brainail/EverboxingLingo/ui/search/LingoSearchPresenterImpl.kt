package org.brainail.EverboxingLingo.ui.search

import org.brainail.EverboxingLingo.ui.RxBasePresenter

class LingoSearchPresenterImpl :
        RxBasePresenter<LingoSearchView, LingoSearchViewState>(), LingoSearchPresenter {

    override fun createInitialViewState(): LingoSearchViewState? {
        return LingoSearchViewState(loading = false)
    }

    override fun applyViewState(view: LingoSearchView, state: LingoSearchViewState, newViewAttached: Boolean) {
    }

}