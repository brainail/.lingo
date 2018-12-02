package org.brainail.everboxing.lingo.util.log

import android.util.Log
import org.brainail.logger.L
import java.util.regex.Pattern

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
     * Extract the class name without any anonymous class suffixes (e.g., `Foo$1`
     * becomes `Foo`).
     */
    private fun extractClassName(element: StackTraceElement): String {
        var tag = element.fileName
        val m = ANONYMOUS_CLASS.matcher(tag)
        if (m.find()) {
            tag = m.replaceAll("")
        }
        return tag
    }

    /**
     * Break up `message` into maximum-length chunks (if needed) and send to either
     */
    @Suppress("ComplexMethod")
    override fun log(priority: L.LogPriority, tag: String, rawMessage: String, t: Throwable?) {
        val androidLogPriority = toAndroidLogPriority(priority)

        // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
        // because Robolectric runs them on the JVM but on Android the elements are different.
        val stackTrace = Throwable().stackTrace
        if (stackTrace.size <= CALL_STACK_INDEX) {
            throw IllegalStateException("Synthetic stacktrace didn't have enough elements: are you using proguard?")
        }

        val clazz = extractClassName(stackTrace[CALL_STACK_INDEX])
        val lineNumber = stackTrace[CALL_STACK_INDEX].lineNumber
        val message = "($clazz:$lineNumber) ⭆⭆⭆ \n$rawMessage"

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
        private const val MAX_LOG_LENGTH = 4000
        private const val MAX_TAG_LENGTH = 23
        private const val CALL_STACK_INDEX = 5
        private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
    }
}
