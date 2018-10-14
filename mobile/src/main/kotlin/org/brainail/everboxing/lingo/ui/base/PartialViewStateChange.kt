package org.brainail.everboxing.lingo.ui.base

interface PartialViewStateChange<VS> {
    fun applyTo(viewState: VS): VS
}