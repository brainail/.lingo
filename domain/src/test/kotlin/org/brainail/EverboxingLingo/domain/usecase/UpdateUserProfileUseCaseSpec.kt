package org.brainail.EverboxingLingo.domain.usecase

import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals

class UpdateUserProfileUseCaseSpec : org.jetbrains.spek.api.Spek({
    given("UpdateUserProfileUseCase") {
        on("some action") {
            it("should pass") {
                assertEquals(1, 1)
            }
        }
    }
})