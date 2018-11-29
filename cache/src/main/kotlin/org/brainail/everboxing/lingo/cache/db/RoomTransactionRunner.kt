package org.brainail.everboxing.lingo.cache.db

import org.brainail.everboxing.lingo.data.util.DatabaseTransactionRunner
import java.util.concurrent.Callable

class RoomTransactionRunner(private val db: LingoDatabase) : DatabaseTransactionRunner {
    override operator fun <T> invoke(run: () -> T): T = db.runInTransaction(Callable<T> { run() })
}