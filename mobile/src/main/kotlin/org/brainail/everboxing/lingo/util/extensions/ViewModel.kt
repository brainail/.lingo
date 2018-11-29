package org.brainail.everboxing.lingo.util.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlin.reflect.KClass

/**
 * [klazz] Shared class
 *
 * [key] Extra key in order make a difference
 *
 * The purposes of this annotation is to give some extra information about
 * a possible shared class between [android.app.Activity] and [Fragment]
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class SharedViewModel(val klazz: KClass<out ViewModel>, val key: String = "")

inline fun <reified VM : ViewModel> FragmentActivity.getViewModel(provider: ViewModelProvider.Factory)
        = ViewModelProviders.of(this, provider).get(VM::class.viewModelProviderKey(), VM::class.java)

inline fun <reified VM : ViewModel> Fragment.getViewModel(provider: ViewModelProvider.Factory)
        = ViewModelProviders.of(this, provider).get(VM::class.viewModelProviderKey(), VM::class.java)

inline fun <reified VM : ViewModel> Fragment.getActivityViewModel(provider: ViewModelProvider.Factory)
        = ViewModelProviders.of(requireActivity(), provider).get(VM::class.viewModelProviderKey(), VM::class.java)

fun KClass<out ViewModel>.viewModelProviderKey(): String {
    val clazz = this.java
    val sharedViewModel = clazz.getAnnotation(SharedViewModel::class.java) ?: return clazz.canonicalName!!
    return sharedViewModel.klazz.java.canonicalName!! + sharedViewModel.key
}