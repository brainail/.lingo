package org.brainail.EverboxingLingo.ui.urban

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import org.brainail.logger.L
import java.util.*
import java.util.concurrent.TimeUnit


class _UrbanSearchViewModel : ViewModel() {

    val search: BehaviorSubject<String> = BehaviorSubject.create()

    private var searchSubscription: Disposable?

    init {
        searchSubscription = search
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe({
                    L.v("onNext: query = $it")
                })
    }

    fun search() {
        val query = Random().nextFloat().toString()
        search.onNext(query)
    }

    override fun onCleared() {
        super.onCleared()
        searchSubscription?.dispose()
    }

}
