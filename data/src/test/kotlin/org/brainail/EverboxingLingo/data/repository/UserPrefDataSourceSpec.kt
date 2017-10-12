package org.brainail.EverboxingLingo.data.repository

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals

object UserPrefDataSourceSpec : Spek({
    given("UserPrefDataSource") {
        on("some action") {
            it("should pass") {
                assertEquals(1, 1)
            }
        }
    }
})