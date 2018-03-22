package org.brainail.EverboxingLingo.ui.home.explore

import android.arch.lifecycle.LiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import org.brainail.EverboxingLingo.domain.usecase.FindSuggestionsUseCase
import org.brainail.EverboxingLingo.ui.RxAwareViewModel
import org.brainail.EverboxingLingo.util.SingleEventLiveData
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LingoSearchFragmentViewModel @Inject constructor(
        private val findSuggestionsUseCase: FindSuggestionsUseCase) : RxAwareViewModel() {

    private val searchSubject: PublishSubject<String> by lazy {
        val subject = PublishSubject.create<String>()
        bindObservable(subject)
        subject
    }

    private val presentSuggestions = SingleEventLiveData<List<String>>()
    private val startSuggestionsLoading = SingleEventLiveData<Void>()

    init {
        searchSubject
                .debounce(500, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap { query ->
                    findSuggestionsUseCase.execute(query)
                            .doOnSubscribe { startSuggestionsLoading.call() }
                            .subscribeOn(AndroidSchedulers.mainThread())
                }
                .subscribe({
                    presentSuggestions.value = it.map { suggestion -> suggestion.word }
                })
    }

    fun presentSuggestions(): LiveData<List<String>> = presentSuggestions
    fun startSuggestionsLoading(): LiveData<Void> = startSuggestionsLoading

    fun searchSuggestions(query: String) {
        searchSubject.onNext(query)
    }

}