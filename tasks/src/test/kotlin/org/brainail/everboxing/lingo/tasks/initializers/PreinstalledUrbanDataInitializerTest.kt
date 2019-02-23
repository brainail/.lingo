/*
 * Copyright 2019 Malyshev Yegor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brainail.everboxing.lingo.tasks.initializers

import android.app.Application
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkClass
import io.mockk.verify
import org.brainail.everboxing.lingo.base.app.AppInitializer
import org.brainail.everboxing.lingo.base.app.tasks.SyncTasks
import org.brainail.everboxing.lingo.domain.settings.SyncSettings
import org.junit.Before
import org.junit.Test

class PreinstalledUrbanDataInitializerTest {

    lateinit var initializer: AppInitializer

    @MockK
    lateinit var syncSettings: SyncSettings
    @MockK(relaxed = true)
    lateinit var syncTasks: SyncTasks

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockKAnnotations.init(this)
        initializer = PreinstalledUrbanDataInitializer(PATH_DATA, syncSettings, syncTasks)
    }

    @Test
    fun `when try to init and data is not initialized then initialize`() {
        every { syncSettings.isPreinstalledUrbanDataInitialized } returns false
        initializer.init(mockkClass(Application::class))
        verify { syncTasks.installUrbanServiceData(PATH_DATA) }
    }

    @Test
    fun `when try to init and data is initialized then nothing to do`() {
        every { syncSettings.isPreinstalledUrbanDataInitialized } returns true
        initializer.init(mockkClass(Application::class))
        verify(exactly = 0) { syncTasks.installUrbanServiceData(any()) }
    }

    companion object {
        const val PATH_DATA = "data.txt"
    }
}
