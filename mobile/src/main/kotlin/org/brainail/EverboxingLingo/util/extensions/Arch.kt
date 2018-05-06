package org.brainail.EverboxingLingo.util.extensions

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer

// TODO("https://github.com/googlesamples/android-architecture-components/issues/47#issuecomment-365959811")
fun <T> LiveData<T>.reObserve(owner: LifecycleOwner, observer: Observer<T>) {
    removeObserver(observer)
    observe(owner, observer)
}
