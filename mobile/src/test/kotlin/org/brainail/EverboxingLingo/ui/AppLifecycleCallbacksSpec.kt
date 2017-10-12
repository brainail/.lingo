package org.brainail.EverboxingLingo.ui

import io.reactivex.observers.TestObserver
import org.brainail.EverboxingLingo.domain.event.EventBus
import org.brainail.EverboxingLingo.domain.event.GlobalEvents
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object AppLifecycleCallbacksSpec : Spek({
    given("AppLifecycleCallbacks") {
        val globalBus: EventBus<GlobalEvents> by memoized { EventBus<GlobalEvents>("global") }
        val observer: AppLifecycleObserver by memoized { AppLifecycleObserver(globalBus) }

        beforeEachTest {
            @Suppress("UNUSED_EXPRESSION")
            observer // force init
        }

        it("should report false by default when subscribed") {
            globalBus.observe()
                    .test()
                    .assertValue(GlobalEvents.UiStateChanged(false))
        }

        group("subscribed") {
            val testObserver: TestObserver<GlobalEvents> by memoized { globalBus.observe().skip(1).test() }

            beforeEachTest {
                @Suppress("UNUSED_EXPRESSION")
                testObserver // force init
            }

            on("start activity") {
                observer.onStart()

                it("should move to foreground") {
                    testObserver.assertValue(GlobalEvents.UiStateChanged(true))
                }
            }

            group("on foreground") {
                val testObserver2: TestObserver<GlobalEvents> by memoized { globalBus.observe().skip(1).test() }

                beforeEachTest {
                    observer.onStart()
                    @Suppress("UNUSED_EXPRESSION")
                    testObserver2 // force init
                }

                on("move to background") {
                    observer.onStop()

                    it("should move to background") {
                        testObserver2.assertValue(GlobalEvents.UiStateChanged(false))
                    }
                }
            }
        }
    }
})