package org.brainail.EverboxingLingo.ui.home

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals

object MainPresenterImplSpec : Spek({
    given("nothing") {

        on("do nothing") {

            it("should equals") {
                assertEquals(0, 0)
            }
        }
    }
})