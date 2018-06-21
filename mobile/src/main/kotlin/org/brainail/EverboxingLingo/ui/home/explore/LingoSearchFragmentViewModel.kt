package org.brainail.EverboxingLingo.ui.home.explore

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.brainail.EverboxingLingo.app.Constants
import org.brainail.EverboxingLingo.domain.executor.AppExecutors
import org.brainail.EverboxingLingo.domain.model.SearchResult
import org.brainail.EverboxingLingo.domain.model.Suggestion
import org.brainail.EverboxingLingo.domain.usecase.FindSearchResultsUseCase
import org.brainail.EverboxingLingo.domain.usecase.FindSuggestionsUseCase
import org.brainail.EverboxingLingo.mapper.SearchResultModelMapper
import org.brainail.EverboxingLingo.mapper.SuggestionModelMapper
import org.brainail.EverboxingLingo.model.SearchResultModel
import org.brainail.EverboxingLingo.model.SuggestionModel
import org.brainail.EverboxingLingo.ui.base.RxAwareViewModel
import org.brainail.EverboxingLingo.util.SingleEventLiveData
import org.brainail.EverboxingLingo.util.extensions.seamlessLoading
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LingoSearchFragmentViewModel @Inject constructor(
        private val findSuggestionsUseCase: FindSuggestionsUseCase,
        private val findSearchResultsUseCase: FindSearchResultsUseCase,
        private val suggestionModelMapper: SuggestionModelMapper,
        private val searchResultModelMapper: SearchResultModelMapper,
        private val appExecutors: AppExecutors) : RxAwareViewModel() {

    private val searchSuggestionsSubject: PublishSubject<String> by lazy {
        val subject = PublishSubject.create<String>()
        bindObservable(subject)
        subject
    }

    private val searchResultsSubject: PublishSubject<String> by lazy {
        val subject = PublishSubject.create<String>()
        bindObservable(subject)
        subject
    }

    private val presentSuggestions = SingleEventLiveData<List<SuggestionModel>>()
    private val startSuggestionsLoading = SingleEventLiveData<Void>()
    private val presentSearchResults = SingleEventLiveData<List<SearchResultModel>>()
    private val startSearchResultsLoading = SingleEventLiveData<Void>()

    fun presentSuggestions(): LiveData<List<SuggestionModel>> = presentSuggestions
    fun startSuggestionsLoading(): LiveData<Void> = startSuggestionsLoading
    fun presentSearchResults(): LiveData<List<SearchResultModel>> = presentSearchResults
    fun startSearchResultsLoading(): LiveData<Void> = startSearchResultsLoading

    init {
        initSuggestions()
        initResults()
    }

    fun searchSuggestions(query: String) {
        searchSuggestionsSubject.onNext(query)
    }

    fun searchResults(query: String) {
        searchResultsSubject.onNext(query)
    }

    @SuppressLint("CheckResult")
    private fun initSuggestions() {
        searchSuggestionsSubject
                .debounce(Constants.DEBOUNCE_REQUEST_MILLIS, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap { findSuggestions(it) }
                .map { it.map { suggestionModelMapper.mapToModel(it) } }
                .observeOn(appExecutors.mainScheduler)
                .subscribe { presentSuggestions.value = it }
    }

    private fun findSuggestions(query: String): Observable<List<Suggestion>> {
        return findSuggestionsUseCase.execute(query)
                .toObservable()
                .doOnSubscribe { startSuggestionsLoading.call() }
                .subscribeOn(appExecutors.mainScheduler) // for doOnSubscribe
    }

    @SuppressLint("CheckResult")
    private fun initResults() {
        searchResultsSubject
                .debounce(Constants.DEBOUNCE_REQUEST_MILLIS, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap { findResults(it) }
                .map { it.map { searchResultModelMapper.mapToModel(it) } }
                .observeOn(appExecutors.mainScheduler)
                .subscribe { presentSearchResults.value = it }
    }

    private fun findResults(query: String): Observable<List<SearchResult>> {
        return findSearchResultsUseCase.execute(query)
                .seamlessLoading()
                .toObservable()
                .doOnSubscribe { startSearchResultsLoading.call() }
                .subscribeOn(appExecutors.mainScheduler) // for doOnSubscribe
    }
}