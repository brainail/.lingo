package org.brainail.EverboxingLingo.ui.home

import android.arch.lifecycle.LiveData
import org.brainail.EverboxingLingo.ui.BaseViewModel
import org.brainail.EverboxingLingo.util.SingleEventLiveData
import javax.inject.Inject

class SearchViewModel @Inject constructor() : BaseViewModel() {

    private val mutableSearchForSuggestionsLiveData = SingleEventLiveData<String>()
    val searchForSuggestionsCall: LiveData<String>
        get() = mutableSearchForSuggestionsLiveData

    private val mutableSearchForResultsLiveData = SingleEventLiveData<String>()
    val searchForResultsCall: LiveData<String>
        get() = mutableSearchForResultsLiveData

    init {
        mutableSearchForSuggestionsLiveData.value = ""
    }

    fun onQueryTextChange(query: String) {
        mutableSearchForSuggestionsLiveData.value = query
    }

    fun onQueryTextSubmit(query: String) {
        mutableSearchForResultsLiveData.value = query
    }

    fun onNewSuggestions() {
        TODO("...")
    }

}