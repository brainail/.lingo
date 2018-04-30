package org.brainail.EverboxingLingo.ui.home.explore

import android.arch.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import org.brainail.EverboxingLingo.domain.model.Suggestion
import org.brainail.EverboxingLingo.domain.usecase.FindSuggestionsUseCase
import org.brainail.EverboxingLingo.mapper.SuggestionViewModelMapper
import org.brainail.EverboxingLingo.model.SuggestionViewModel
import org.brainail.EverboxingLingo.ui.RxAwareViewModel
import org.brainail.EverboxingLingo.util.SingleEventLiveData
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LingoSearchFragmentViewModel @Inject constructor(
        private val findSuggestionsUseCase: FindSuggestionsUseCase,
        private val suggestionViewModelMapper: SuggestionViewModelMapper) : RxAwareViewModel() {

    private val searchSuggestionsSubject: PublishSubject<String> by lazy {
        val subject = PublishSubject.create<String>()
        bindObservable(subject)
        subject
    }

    private val presentSuggestions = SingleEventLiveData<List<SuggestionViewModel>>()
    private val startSuggestionsLoading = SingleEventLiveData<Void>()

    fun presentSuggestions(): LiveData<List<SuggestionViewModel>> = presentSuggestions
    fun startSuggestionsLoading(): LiveData<Void> = startSuggestionsLoading

    init {
        searchSuggestionsSubject
                .debounce(700, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap { findSuggestions(it) }
                .subscribe({
                    presentSuggestions.value = it.map {
                        suggestionViewModelMapper.mapToViewModel(it)
                    }
                })
    }

    private fun findSuggestions(query: String): Observable<List<Suggestion>> {
        return findSuggestionsUseCase.execute(query)
                .toObservable()
                .doOnSubscribe { startSuggestionsLoading.call() }
                .subscribeOn(AndroidSchedulers.mainThread())
    }

    fun searchSuggestions(query: String) {
        searchSuggestionsSubject.onNext(query)
    }

}