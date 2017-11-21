package org.brainail.EverboxingLingo.util

import android.util.Log
import org.brainail.logger.L

/**
 * A tree for debug builds.
 * Automatically infers the tag from the calling class.
 */
class AndroidLogTree : L.Tree() {
    /**
     * Extract the tag which should be used for the message from the `element`. By default
     * this will use the class name without any anonymous class suffixes (e.g., `Foo$1`
     * becomes `Foo`).
     *
     * Note: This will not be called if a manual tag was specified.
     */
    private fun createStackElementTag(element: StackTraceElement): String {
        var tag = element.className
        val anonymousIndex = tag.indexOf('$')
        if (anonymousIndex >= 0) {
            tag = tag.substring(0, tag.indexOf('$'))
        }
        tag = tag.substring(tag.lastIndexOf('.') + 1)
        return if (tag.length > MAX_TAG_LENGTH) tag.substring(0, MAX_TAG_LENGTH) else tag
    }

    override fun getTag(): String {
        val tag = super.getTag()
        if (tag != null) {
            return tag
        }

        // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
        // because Robolectric runs them on the JVM but on Android the elements are different.
        val stackTrace = Throwable().stackTrace
        if (stackTrace.size <= CALL_STACK_INDEX) {
            throw IllegalStateException("Synthetic stacktrace didn't have enough elements: are you using proguard?")
        }

        return createStackElementTag(stackTrace[CALL_STACK_INDEX])
    }

    /**
     * Break up `message` into maximum-length chunks (if needed) and send to either
     */
    override fun log(priority: L.LogPriority, tag: String, message: String, t: Throwable?) {
        val androidLogPriority = toAndroidLogPriority(priority)

        if (message.length < MAX_LOG_LENGTH) {
            when (priority) {
                L.LogPriority.ASSERT -> Log.wtf(tag, message)
                else -> Log.println(androidLogPriority, tag, message)
            }
            return
        }

        // Split by line, then ensure each line can fit into Log's maximum length.
        var i = 0
        val length = message.length
        while (i < length) {
            var newline = message.indexOf('\n', i)
            newline = if (newline != -1) newline else length
            do {
                val end = Math.min(newline, i + MAX_LOG_LENGTH)
                val part = message.substring(i, end)
                when (priority) {
                    L.LogPriority.ASSERT -> Log.wtf(tag, part)
                    else -> Log.println(androidLogPriority, tag, part)
                }
                i = end
            } while (i < newline)
            i++
        }
    }

    private fun toAndroidLogPriority(priority: L.LogPriority): Int = when (priority) {
        L.LogPriority.VERBOSE -> Log.VERBOSE
        L.LogPriority.DEBUG -> Log.DEBUG
        L.LogPriority.INFO -> Log.INFO
        L.LogPriority.WARN -> Log.WARN
        L.LogPriority.ERROR -> Log.ERROR
        L.LogPriority.ASSERT -> Log.ASSERT
    }

    companion object {
        const private val MAX_LOG_LENGTH = 4000
        const private val MAX_TAG_LENGTH = 23
        const private val CALL_STACK_INDEX = 5
    }
}