package org.brainail.EverboxingLingo.ui.home

import android.arch.lifecycle.LiveData
import org.brainail.EverboxingLingo.ui.BaseViewModel
import org.brainail.EverboxingLingo.util.SingleEventLiveData
import javax.inject.Inject

class SearchViewModel @Inject constructor() : BaseViewModel() {

    private val searchForSuggestionsLiveData = SingleEventLiveData<String>()
    val searchForSuggestionsCall: LiveData<String>
        get() = searchForSuggestionsLiveData

    private val searchForResultsLiveData = SingleEventLiveData<String>()
    val searchForResultsCall: LiveData<String>
        get() = searchForResultsLiveData

    init {
        searchForSuggestionsLiveData.value = ""
    }

    fun updateQuery(query: String) {
        searchForSuggestionsLiveData.value = query
    }

    fun submitQuery(query: String) {
        searchForResultsLiveData.value = query
    }

    fun updateSuggestions() {
        TODO("...")
    }

}