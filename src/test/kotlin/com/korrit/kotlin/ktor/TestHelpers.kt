package com.korrit.kotlin.ktor

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

internal fun <T> List<T>.testCases(test: T.() -> Unit) {
    withIndex().forEach { (index, case) ->
        try {
            test(case)
        } catch (e: Throwable) {
            throw AssertionError("Case $index failed - $case", e)
        }
    }
}

internal fun <T> Map<String, T>.testCases(test: T.() -> Unit) {
    forEach { (name, case) ->
        try {
            test(case)
        } catch (e: Throwable) {
            throw AssertionError("Case '$name' failed - $case", e)
        }
    }
}

internal fun eventually(timeout: Long, test: () -> Unit) {
    runBlocking {
        var lastError: java.lang.AssertionError? = null
        try {
            withTimeout(timeout) {
                var i = 0L
                while (true) {
                    try {
                        test()
                        return@withTimeout
                    } catch (e: AssertionError) {
                        lastError = e
                        delay(i++ * 1000)
                    }
                }
            }
        } catch (e: TimeoutCancellationException) {
            throw AssertionError("Failed to eventually verify test after $timeout milliseconds", lastError ?: e)
        }
    }
}
