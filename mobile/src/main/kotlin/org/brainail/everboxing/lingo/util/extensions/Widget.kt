@file:JvmName("WidgetUtils")

package org.brainail.everboxing.lingo.util.extensions

import android.text.Editable
import org.brainail.everboxing.lingo.util.TextWatcherAdapter
import org.brainail.flysearch.FloatingSearchView

inline fun FloatingSearchView.setAfterTextChangedListener(crossinline action: (Editable) -> Unit) {
    addTextChangedListener(object : TextWatcherAdapter() {
        override fun afterTextChanged(query: Editable) {
            action(query)
        }
    })
}
