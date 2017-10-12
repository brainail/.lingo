package org.brainail.EverboxingLingo.util.log

import org.brainail.EverboxingLingo.domain.event.EventBus
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals

object EventBusLoggerSpec : Spek({
    given("EventBusLogger") {
        val eventBuses: Array<EventBus<Any>> = arrayOf(EventBus("global"), EventBus("local"))
        var logger = EventBusLogger()

        beforeEachTest {
            logger = EventBusLogger(*eventBuses)
        }

        it("should be disabled by default") {
            assertEquals(false, logger.enabled)
        }

        it("should not register event buses by default") {
            assertEquals(0, logger.disposable.size())
        }

        group("enabled") {

            beforeEachTest {
                logger.enabled = true
            }

            it("should become enabled") {
                assertEquals(true, logger.enabled)
            }

            it("should register event buses") {
                assertEquals(eventBuses.size, logger.disposable.size())
            }

            on("enable again") {
                logger.enabled = true

                it("should not register event buses twice") {
                    assertEquals(eventBuses.size, logger.disposable.size())
                }
            }

            on("disable") {
                logger.enabled = false

                it("should become disabled") {
                    assertEquals(false, logger.enabled)
                }

                it("should unregister event buses") {
                    assertEquals(0, logger.disposable.size())
                }
            }
        }

    }
})