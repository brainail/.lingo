package org.brainail.EverboxingLingo.internalparcel

import org.brainail.EverboxingLingo.internalparcel.adapter.StubParcelableAdapter
import paperparcel.Adapter
import paperparcel.ProcessorConfig

@Suppress("unused")
@ProcessorConfig(adapters = arrayOf(
        Adapter(StubParcelableAdapter::class, nullSafe = true)
))
interface Config