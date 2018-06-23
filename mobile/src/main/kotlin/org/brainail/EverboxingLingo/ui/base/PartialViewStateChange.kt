package org.brainail.EverboxingLingo.ui.base

interface PartialViewStateChange<VS> {
    fun applyTo(viewState: VS): VS
}