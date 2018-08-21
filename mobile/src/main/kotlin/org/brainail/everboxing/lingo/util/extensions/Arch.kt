package org.brainail.everboxing.lingo.util.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

// TODO("https://github.com/googlesamples/android-architecture-components/issues/47#issuecomment-365959811")
fun <T> LiveData<T>.reObserve(owner: LifecycleOwner, observer: Observer<T>) {
    removeObserver(observer)
    observe(owner, observer)
}
