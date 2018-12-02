package org.brainail.everboxing.lingo.base.util

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

inline fun consume(f: () -> Unit): Boolean {
    f()
    return true
}

inline fun safeTry(block: () -> Unit) {
    try {
        block()
    } catch (ignore: Throwable) {
        // do nothing
    }
}

@Suppress("TooGenericExceptionCaught")
inline fun <T> tryOrRuntime(block: () -> T): T {
    return try {
        block()
    } catch (cause: Throwable) {
        throw RuntimeException(cause)
    }
}

