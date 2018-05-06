package org.brainail.EverboxingLingo.util.paperParcel

import org.brainail.EverboxingLingo.util.paperParcel.adapter.StubParcelableAdapter
import paperparcel.Adapter
import paperparcel.ProcessorConfig

@Suppress("unused")
@ProcessorConfig(adapters = arrayOf(
        Adapter(StubParcelableAdapter::class, nullSafe = true)
))
interface Config