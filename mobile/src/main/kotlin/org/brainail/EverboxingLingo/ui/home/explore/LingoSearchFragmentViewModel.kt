package org.brainail.EverboxingLingo.ui.home.explore

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.brainail.EverboxingLingo.domain.executor.RxExecutor
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
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LingoSearchFragmentViewModel @Inject constructor(
        private val findSuggestionsUseCase: FindSuggestionsUseCase,
        private val findSearchResultsUseCase: FindSearchResultsUseCase,
        private val suggestionModelMapper: SuggestionModelMapper,
        private val searchResultModelMapper: SearchResultModelMapper,
        private val rxExecutor: RxExecutor) : RxAwareViewModel() {

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
                .debounce(700, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap { findSuggestions(it) }
                .map { it.map { suggestionModelMapper.mapToModel(it) } }
                .observeOn(rxExecutor.mainScheduler)
                .subscribe({ presentSuggestions.value = it })
    }

    private fun findSuggestions(query: String): Observable<List<Suggestion>> {
        return findSuggestionsUseCase.execute(query)
                .toObservable()
                .doOnSubscribe { startSuggestionsLoading.call() }
                .subscribeOn(rxExecutor.mainScheduler) // for doOnSubscribe
    }

    @SuppressLint("CheckResult")
    private fun initResults() {
        searchResultsSubject
                .debounce(700, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap { findResults(it) }
                .map { it.map { searchResultModelMapper.mapToModel(it) } }
                .observeOn(rxExecutor.mainScheduler)
                .subscribe({ presentSearchResults.value = it })
    }

    private fun findResults(query: String): Observable<List<SearchResult>> {
        return findSearchResultsUseCase.execute(query)
                .toObservable()
                .doOnSubscribe { startSearchResultsLoading.call() }
                .subscribeOn(rxExecutor.mainScheduler) // for doOnSubscribe
        // return Observable.just(listOf(SearchResult("21", query, "$query it is ...", "I like when you say - $query")))
    }
}