package org.brainail.EverboxingLingo.ui.home.explore

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import org.brainail.EverboxingLingo.app.Constants
import org.brainail.EverboxingLingo.domain.executor.AppExecutors
import org.brainail.EverboxingLingo.domain.model.SearchResult
import org.brainail.EverboxingLingo.domain.model.Suggestion
import org.brainail.EverboxingLingo.domain.usecase.FindRecentSuggestionsUseCase
import org.brainail.EverboxingLingo.domain.usecase.FindSearchResultsUseCase
import org.brainail.EverboxingLingo.domain.usecase.FindSuggestionsUseCase
import org.brainail.EverboxingLingo.domain.usecase.SaveRecentSuggestionUseCase
import org.brainail.EverboxingLingo.mapper.SearchResultModelMapper
import org.brainail.EverboxingLingo.mapper.SuggestionModelMapper
import org.brainail.EverboxingLingo.model.SuggestionModel
import org.brainail.EverboxingLingo.ui.base.PartialViewStateChange
import org.brainail.EverboxingLingo.ui.base.RxAwareViewModel
import org.brainail.EverboxingLingo.ui.home.explore.LingoSearchFragmentViewState.SearchResultsPrepared
import org.brainail.EverboxingLingo.ui.home.explore.LingoSearchFragmentViewState.SearchResultsStartedLoading
import org.brainail.EverboxingLingo.util.SingleEventLiveData
import org.brainail.EverboxingLingo.util.extensions.seamlessLoading
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LingoSearchFragmentViewModel @Inject constructor(
        private val findSuggestionsUseCase: FindSuggestionsUseCase,
        private val findRecentSuggestionsUseCase: FindRecentSuggestionsUseCase,
        private val findSearchResultsUseCase: FindSearchResultsUseCase,
        private val saveRecentSuggestionUseCase: SaveRecentSuggestionUseCase,
        private val suggestionModelMapper: SuggestionModelMapper,
        private val searchResultModelMapper: SearchResultModelMapper,
        private val appExecutors: AppExecutors) : RxAwareViewModel() {

    private val viewState = MutableLiveData<LingoSearchFragmentViewState>()

    private val searchSuggestionsSubject: PublishSubject<String> by lazy {
        val subject = PublishSubject.create<String>()
        bindObservable(subject)
        subject
    }

    private val searchResultsSubject: PublishSubject<SuggestionModel> by lazy {
        val subject = PublishSubject.create<SuggestionModel>()
        bindObservable(subject)
        subject
    }

    private val presentSuggestions = SingleEventLiveData<List<SuggestionModel>>()
    private val startSuggestionsLoading = SingleEventLiveData<Void>()

    fun presentSuggestions(): LiveData<List<SuggestionModel>> = presentSuggestions
    fun startSuggestionsLoading(): LiveData<Void> = startSuggestionsLoading
    fun viewState(): LiveData<LingoSearchFragmentViewState> = viewState

    init {
        initSuggestions()
        initResults()
        viewState.value = LingoSearchFragmentViewState.INITIAL
    }

    fun searchSuggestions(query: String) {
        searchSuggestionsSubject.onNext(query)
    }

    fun searchResults(suggestion: SuggestionModel) {
        searchResultsSubject.onNext(suggestion)
    }

    @SuppressLint("CheckResult")
    private fun initSuggestions() {
        searchSuggestionsSubject
                .debounce(Constants.DEBOUNCE_SEARCH_REQUEST_MILLIS, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap { findSuggestions(it) }
                .map { it.map { suggestionModelMapper.mapToModel(it) } }
                .observeOn(appExecutors.mainScheduler)
                .subscribe { presentSuggestions.value = it }
    }

    private fun findSuggestions(query: String): Observable<List<Suggestion>> {
        val suggestions: Flowable<List<Suggestion>> = Flowable.combineLatest(
                findRecentSuggestionsUseCase.execute(query),
                findSuggestionsUseCase.execute(query),
                BiFunction { recent, remote -> recent + remote })
        return suggestions
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
                    it.takeIf { it.word.isNotBlank() && !it.isSilent }
                            ?.run { saveRecentSuggestion(it).subscribe() }
                }
                .switchMap { findResults(it.word.toString()) }
                .map { it.map { searchResultModelMapper.mapToModel(it) } }
                .observeOn(appExecutors.mainScheduler)
                .subscribe { applyChanges(SearchResultsPrepared(it)) }
    }

    private fun findResults(query: String): Observable<List<SearchResult>> {
        return findSearchResultsUseCase.execute(query)
                .seamlessLoading()
                .toObservable()
                .doOnSubscribe { applyChanges(SearchResultsStartedLoading) }
                .subscribeOn(appExecutors.mainScheduler) // for doOnSubscribe
    }

    private fun saveRecentSuggestion(suggestion: SuggestionModel): Completable {
        return saveRecentSuggestionUseCase.execute(suggestionModelMapper.mapFromModel(suggestion))
    }

    private fun applyChanges(partialViewStateChange: PartialViewStateChange<LingoSearchFragmentViewState>)
            : LingoSearchFragmentViewState {
        viewState.value = partialViewStateChange.applyTo(viewState = viewState.value!!)
        return viewState.value!!
    }
}