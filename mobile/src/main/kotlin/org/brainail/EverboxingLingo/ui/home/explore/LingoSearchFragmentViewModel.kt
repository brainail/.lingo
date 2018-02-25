package org.brainail.EverboxingLingo.ui.home.explore

import android.arch.lifecycle.LiveData
import android.os.SystemClock
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.brainail.EverboxingLingo.ui.RxAwareViewModel
import org.brainail.EverboxingLingo.util.SingleEventLiveData
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LingoSearchFragmentViewModel @Inject constructor() : RxAwareViewModel() {

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
                    findSuggestions(query)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe { startSuggestionsLoading.call() }
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .onErrorReturn { emptyList() }
                }
                .subscribe({
                    presentSuggestions.value = it
                })
    }

    fun presentSuggestions(): LiveData<List<String>> = presentSuggestions
    fun startSuggestionsLoading(): LiveData<Void> = startSuggestionsLoading

    fun searchSuggestions(query: String) {
        searchSubject.onNext(query)
    }

    private fun findSuggestions(query: String): Observable<List<String>> = Observable.fromCallable {
        // https://api.urbandictionary.com/v0/autocomplete?term=holymoly
        // https//api.urbandictionary.com/v0/autocomplete-extra?term=holymoly
        // https://api.urbandictionary.com/v0/define with ?term=WORD_HERE or ?defid=DEFID_HERE
        // https://api.urbandictionary.com/v0/random
        // https://api.urbandictionary.com/v0/vote POST: {defid: 665139, direction: "up"}
        SystemClock.sleep(2000)
        if (query.startsWith("ex")) {
            throw RuntimeException()
        } else {
            listOf("0. " + query, "1. " + query)
        }
    }

}