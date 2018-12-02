package org.brainail.everboxing.lingo.util

import androidx.lifecycle.ViewModel
import kotlin.reflect.KClass

/**
 * [klazz] Shared class
 *
 * [key] Extra key in order make a difference
 *
 * The purposes of this annotation is to give some extra information about
 * a possible shared class between [android.app.Activity] and [androidx.fragment.app.Fragment]
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class SharedViewModel(val klazz: KClass<out ViewModel>, val key: String = "")
