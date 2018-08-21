package org.brainail.everboxing.lingo.util.extensions

/**
 * Implementation of lazy that is not thread safe.
 */
fun <T> lazyFast(operation: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) {
    operation()
}

/**
 * Force a when statement to check all options.
 */
val <T> T.checkAllMatched: T
    get() = this


