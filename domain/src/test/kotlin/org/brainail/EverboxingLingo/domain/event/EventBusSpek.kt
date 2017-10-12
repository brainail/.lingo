package org.brainail.EverboxingLingo.domain.event

import io.reactivex.observers.TestObserver
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object EventBusSpek : Spek({
    given("EventBus") {
        var bus: EventBus<TestEvents> = EventBus("test")

        beforeEachTest {
            bus = EventBus("test")
        }

        group("subscribed") {
            var test: TestObserver<TestEvents> = TestObserver.create()

            beforeEachTest {
                test = bus.observe().test()
            }

            it("should be empty") {
                test.assertEmpty().assertNoValues()
            }

            it("should never complete") {
                test.assertNotComplete()
            }

            it("should never fail") {
                test.assertNoErrors()
            }

            on("post multiple non-sticky events") {
                bus.post(TestEvents.SingletonEvent)
                bus.post(TestEvents.UiStateChanged(true))
                bus.post(TestEvents.UiStateChanged(false))

                it("should emit them in order") {
                    test.assertValues(TestEvents.SingletonEvent, TestEvents.UiStateChanged(true), TestEvents.UiStateChanged(false))
                }
            }

            on("post multiple sticky events") {
                bus.postSticky(TestEvents.UiStateChanged(false))
                bus.postSticky(TestEvents.UiStateChanged(true))

                it("should emit them in order") {
                    test.assertValues(TestEvents.UiStateChanged(false), TestEvents.UiStateChanged(true))
                }
            }
        }

        group("not subscribed") {

            on("post sticky event") {
                bus.postSticky(TestEvents.SingletonEvent)

                it("should emit that sticky event when subscribed") {
                    bus.observe()
                            .test()
                            .assertValue(TestEvents.SingletonEvent)
                }

            }

            on("post multiple sticky events of the same type") {
                bus.postSticky(TestEvents.UiStateChanged(true))
                bus.postSticky(TestEvents.UiStateChanged(false))

                it("should emit last event only when subscribed") {
                    bus.observe()
                            .test()
                            .assertValue(TestEvents.UiStateChanged(false))
                }
            }

            on("post multiple sticky events of different type") {
                bus.postSticky(TestEvents.SingletonEvent)
                bus.postSticky(TestEvents.UiStateChanged(false))
                bus.postSticky(TestEvents.UiStateChanged(true))

                it("should emit last value per each event type when subscribed") {
                    bus.observe()
                            .test()
                            .assertValueCount(2)
                            .assertValueSet(setOf(TestEvents.SingletonEvent, TestEvents.UiStateChanged(true)))
                }
            }

            on("post multiple sticky events of different type and then remove one") {
                bus.postSticky(TestEvents.UiStateChanged(false))
                bus.postSticky(TestEvents.SingletonEvent)
                bus.removeSticky(TestEvents.UiStateChanged::class)

                it("should not emit removed event when subscribed") {
                    bus.observe()
                            .test()
                            .assertValue(TestEvents.SingletonEvent)
                }
            }

            on("post sticky and non-sticky event of same type") {
                bus.postSticky(TestEvents.UiStateChanged(true))
                bus.post(TestEvents.UiStateChanged(false))

                it("should emit sticky event only when subscribed") {
                    bus.observe()
                            .test()
                            .assertValue(TestEvents.UiStateChanged(true))
                }
            }

            on("post non-sticky event") {
                bus.post(TestEvents.SingletonEvent)

                it("should not emit anything when subscribed") {
                    bus.observe()
                            .test()
                            .assertNoValues()
                }
            }
        }
    }
})

sealed class TestEvents {
    data class UiStateChanged(val isOnForeground: Boolean) : TestEvents()
    object SingletonEvent : TestEvents()

    override fun toString(): String = javaClass.simpleName // to have a nice output on non-data classes
}