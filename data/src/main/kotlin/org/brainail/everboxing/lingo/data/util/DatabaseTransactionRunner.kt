package org.brainail.everboxing.lingo.data.util

interface DatabaseTransactionRunner {
    operator fun <T> invoke(run: () -> T): T
}
