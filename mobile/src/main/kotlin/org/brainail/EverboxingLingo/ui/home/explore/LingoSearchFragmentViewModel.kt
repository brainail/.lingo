package org.brainail.EverboxingLingo.ui.home.explore

import android.arch.lifecycle.LiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
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

    private val searchSubject: PublishSubject<String> by lazy {
        val subject = PublishSubject.create<String>()
        bindObservable(subject)
        subject
    }

    private val presentSuggestions = SingleEventLiveData<List<SuggestionViewModel>>()
    private val startSuggestionsLoading = SingleEventLiveData<Void>()

    init {
        searchSubject
                .debounce(500, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap { query ->
                    findSuggestionsUseCase.execute(query)
                            .toObservable()
                            .doOnSubscribe { startSuggestionsLoading.call() }
                            .subscribeOn(AndroidSchedulers.mainThread())
                }
                .subscribe({
                    presentSuggestions.value = it.map { suggestionViewModelMapper.mapToViewModel(it) }
                })
    }

    fun presentSuggestions(): LiveData<List<SuggestionViewModel>> = presentSuggestions
    fun startSuggestionsLoading(): LiveData<Void> = startSuggestionsLoading

    fun searchSuggestions(query: String) {
        searchSubject.onNext(query)
    }

}