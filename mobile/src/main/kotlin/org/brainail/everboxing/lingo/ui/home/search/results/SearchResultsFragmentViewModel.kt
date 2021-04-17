/*
 * Copyright 2018 Malyshev Yegor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brainail.everboxing.lingo.ui.home.search.results

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.brainail.everboxing.lingo.app.Constants
import org.brainail.everboxing.lingo.base.util.lazyFast
import org.brainail.everboxing.lingo.domain.executor.AppExecutors
import org.brainail.everboxing.lingo.domain.model.SearchResult
import org.brainail.everboxing.lingo.domain.model.Suggestion
import org.brainail.everboxing.lingo.domain.usecase.ForgetSearchResultUseCase
import org.brainail.everboxing.lingo.domain.usecase.SaveSearchResultInHistoryUseCase
import org.brainail.everboxing.lingo.domain.usecase.SaveSuggestionUseCase
import org.brainail.everboxing.lingo.domain.usecase.ToggleSearchResultInFavoritesUseCase
import org.brainail.everboxing.lingo.mapper.SearchResultModelMapper
import org.brainail.everboxing.lingo.mapper.SuggestionModelMapper
import org.brainail.everboxing.lingo.model.SearchResultModel
import org.brainail.everboxing.lingo.model.SuggestionModel
import org.brainail.everboxing.lingo.model.toRecent
import org.brainail.everboxing.lingo.ui.base.PartialViewStateChange
import org.brainail.everboxing.lingo.ui.base.RxAwareViewModel
import org.brainail.everboxing.lingo.ui.home.search.results.SearchResultsFragmentViewState.SearchResultsPrepared
import org.brainail.everboxing.lingo.ui.home.search.results.SearchResultsFragmentViewState.SearchResultsStartedLoading
import org.brainail.everboxing.lingo.util.SingleEventLiveData
import org.brainail.everboxing.lingo.util.extensions.seamlessLoading
import java.util.concurrent.TimeUnit

abstract class SearchResultsFragmentViewModel(
    private val saveRecentSuggestionUseCase: SaveSuggestionUseCase,
    private val toggleSearchResultInFavoritesUseCase: ToggleSearchResultInFavoritesUseCase,
    private val saveSearchResultInHistoryUseCase: SaveSearchResultInHistoryUseCase,
    private val forgetSearchResultUseCase: ForgetSearchResultUseCase,
    private val suggestionModelMapper: SuggestionModelMapper,
    private val searchResultModelMapper: SearchResultModelMapper,
    private val appExecutors: AppExecutors
) : RxAwareViewModel() {

    private val searchSuggestionsSubject: PublishSubject<String> by lazyFast {
        val subject = PublishSubject.create<String>()
        bindObservable(subject)
        subject
    }

    private val searchResultsSubject: PublishSubject<SuggestionModel> by lazyFast {
        val subject = PublishSubject.create<SuggestionModel>()
        bindObservable(subject)
        subject
    }

    private val viewState = MutableLiveData<SearchResultsFragmentViewState>()
    private val presentSuggestions = SingleEventLiveData<List<SuggestionModel>>()
    private val startSuggestionsLoading = SingleEventLiveData<Void>()
    private val navigateToSearchResultEvent = SingleEventLiveData<SearchResultModel>()

    fun presentSuggestions(): LiveData<List<SuggestionModel>> = presentSuggestions
    fun startSuggestionsLoading(): LiveData<Void> = startSuggestionsLoading
    fun navigateToSearchResultEvent(): LiveData<SearchResultModel> = navigateToSearchResultEvent

    fun viewState(): LiveData<SearchResultsFragmentViewState> = viewState

    init {
        initSuggestions()
        initResults()
        viewState.value = SearchResultsFragmentViewState.INITIAL
    }

    /**
     * Executes a usecase related to suggestions and nothing more.
     */
    abstract fun executeFindSuggestionsUseCase(query: String): Flowable<List<Suggestion>>

    /**
     * Executes a usecase related to search results and nothing more.
     */
    abstract fun executeFindSearchResultsUseCase(query: String): Flowable<List<SearchResult>>

    fun searchSuggestions(query: String) {
        searchSuggestionsSubject.onNext(query)
    }

    fun searchResults(suggestion: SuggestionModel) {
        searchResultsSubject.onNext(suggestion)
    }

    fun forgetSearchResultAt(position: Int) {
        val item = viewState.value!!.displayedSearchResults.getOrNull(position) ?: return
        applyChanges(SearchResultsFragmentViewState.ForgetSearchResult(item)) // optimistic update
        forgetSearchResultUseCase.execute(item.id).subscribe()
    }

    fun favoriteSearchResultAt(position: Int) {
        val item = viewState.value!!.displayedSearchResults.getOrNull(position) ?: return
        applyChanges(SearchResultsFragmentViewState.FavoriteSearchResult(item)) // optimistic update
        toggleSearchResultInFavoritesUseCase.execute(item.id).subscribe()
    }

    fun searchResultClicked(item: SearchResultModel) {
        navigateToSearchResultEvent.value = item
        saveSearchResultInHistoryUseCase.execute(item.id).subscribe()
    }

    @SuppressLint("CheckResult")
    private fun initSuggestions() {
        searchSuggestionsSubject
            .debounce(Constants.DEBOUNCE_SEARCH_REQUEST_MILLIS, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap { findSuggestions(it) }
            .map { it.map { suggestion -> suggestionModelMapper.mapF(suggestion) } }
            .observeOn(appExecutors.mainScheduler)
            .subscribe { presentSuggestions.value = it }
    }

    private fun findSuggestions(query: String): Observable<List<Suggestion>> {
        return executeFindSuggestionsUseCase(query)
            .toObservable()
            .doOnSubscribe { startSuggestionsLoading.call() }
            .subscribeOn(appExecutors.mainScheduler) // for doOnSubscribe
    }

    @SuppressLint("CheckResult")
    private fun initResults() {
        searchResultsSubject
            .debounce(Constants.DEBOUNCE_SEARCH_REQUEST_MILLIS, TimeUnit.MILLISECONDS)
            .distinctUntilChanged { cur, next ->
                cur.word == next.word && cur.isSilent == next.isSilent
            }
            .doOnNext {
                it.takeIf { suggestion -> suggestion.word.isNotBlank() && !suggestion.isSilent }
                    ?.let { suggestion -> saveRecentSuggestion(suggestion).subscribe() }
            }
            .switchMap { findResults(it.word.toString()) }
            .map { it.map { searchResult -> searchResultModelMapper.mapF(searchResult) } }
            .observeOn(appExecutors.mainScheduler)
            .subscribe { applyChanges(SearchResultsPrepared(it)) }
    }

    private fun findResults(query: String): Observable<List<SearchResult>> {
        return executeFindSearchResultsUseCase(query)
            .seamlessLoading()
            .toObservable()
            .doOnSubscribe { applyChanges(SearchResultsStartedLoading) }
            .subscribeOn(appExecutors.mainScheduler) // for doOnSubscribe
    }

    private fun saveRecentSuggestion(suggestion: SuggestionModel): Completable {
        return saveRecentSuggestionUseCase.execute(suggestionModelMapper.mapT(suggestion.toRecent()))
    }

    // TODO: Search for completely new word and this one will be called OMG times!!!!
    private fun applyChanges(
        partialViewStateChange: PartialViewStateChange<SearchResultsFragmentViewState>
    ): SearchResultsFragmentViewState {
        viewState.value = partialViewStateChange.applyTo(viewState.value!!)
        return viewState.value!!
    }
}
