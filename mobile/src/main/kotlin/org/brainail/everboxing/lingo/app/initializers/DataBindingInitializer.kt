package org.brainail.everboxing.lingo.app.initializers

import android.app.Application
import androidx.databinding.DataBindingUtil
import org.brainail.everboxing.lingo.base.app.AppInitializer
import org.brainail.everboxing.lingo.binding.LingoDataBindingComponent
import javax.inject.Inject

class DataBindingInitializer @Inject constructor(
        private val dataBindingComponent: LingoDataBindingComponent) : AppInitializer {

    override fun init(application: Application) {
        DataBindingUtil.setDefaultComponent(dataBindingComponent)
    }
}