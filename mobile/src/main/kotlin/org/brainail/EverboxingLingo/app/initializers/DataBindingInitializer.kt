package org.brainail.EverboxingLingo.app.initializers

import android.app.Application
import android.databinding.DataBindingUtil
import org.brainail.EverboxingLingo.binding.LingoDataBindingComponent
import javax.inject.Inject

class DataBindingInitializer @Inject constructor(
        private val dataBindingComponent: LingoDataBindingComponent) : AppInitializer {
    override fun init(application: Application) {
        DataBindingUtil.setDefaultComponent(dataBindingComponent)
    }
}