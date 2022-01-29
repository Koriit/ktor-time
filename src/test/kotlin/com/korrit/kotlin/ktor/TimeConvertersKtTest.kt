package com.korrit.kotlin.ktor

import io.ktor.application.ApplicationCallPipeline
import io.ktor.features.DataConversion
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.chrono.ChronoZonedDateTime

internal class TimeConvertersKtTest {

    @Test
    fun `should convert date and time`() {
        val converter = DataConversion.install(ApplicationCallPipeline()) {
            convertTime()
        }

        data class Case(
            val obj: Any,
            val text: String
        )

        listOf(
            Case(
                obj = LocalDate.of(1993, 12, 11),
                text = "1993-12-11"
            ),
            Case(
                obj = LocalDateTime.of(1993, 12, 11, 20, 15),
                text = "1993-12-11T20:15:00"
            ),
            Case(
                obj = OffsetDateTime.of(1993, 12, 11, 20, 15, 0, 0, ZoneOffset.UTC),
                text = "1993-12-11T20:15:00Z"
            ),
            Case(
                obj = OffsetDateTime.of(1993, 12, 11, 20, 15, 0, 0, ZoneOffset.of("+02:00")),
                text = "1993-12-11T20:15:00+02:00"
            ),
            Case(
                obj = ZonedDateTime.of(1993, 12, 11, 20, 15, 0, 0, ZoneOffset.UTC),
                text = "1993-12-11T20:15:00Z"
            ),
            Case(
                obj = ZonedDateTime.of(1993, 12, 11, 20, 15, 0, 0, ZoneOffset.of("+02:00")),
                text = "1993-12-11T20:15:00+02:00"
            ),
            Case(
                obj = LocalTime.of(20, 15),
                text = "20:15:00"
            )
        ).testCases {
            assertEquals(listOf(text), converter.toValues(obj))
            when (obj) {
                is ChronoZonedDateTime<*> -> assertTrue(obj.isEqual(converter.fromValues(listOf(text), obj::class.java) as ChronoZonedDateTime<*>))
                is OffsetDateTime -> assertTrue(obj.isEqual(converter.fromValues(listOf(text), obj::class.java) as OffsetDateTime))
                else -> assertEquals(obj, converter.fromValues(listOf(text), obj::class.java))
            }
        }
    }
}
