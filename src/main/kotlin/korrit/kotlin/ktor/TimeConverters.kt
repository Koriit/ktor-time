@file:Suppress("TooManyFunctions") // there are just that many time types

package korrit.kotlin.ktor

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.features.DataConversion
import io.ktor.util.*
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.MonthDay
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.Period
import java.time.Year
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

/**
 * Adds converter for JSR310 Java Time types.
 *
 * @param jackson object mapper that performs actual serialization and deserialization
 */
fun DataConversion.Configuration.convertJSR310Time(jackson: ObjectMapper = jackson()) {
    convert<Instant>(jackson)
    convert<OffsetDateTime>(jackson)
    convert<ZonedDateTime>(jackson)
    convert<Duration>(jackson)
    convert<LocalDateTime>(jackson)
    convert<LocalDate>(jackson)
    convert<LocalTime>(jackson)
    convert<MonthDay>(jackson)
    convert<OffsetTime>(jackson)
    convert<Period>(jackson)
    convert<Year>(jackson)
    convert<YearMonth>(jackson)
    convert<ZoneId>(jackson)
    convert<ZoneOffset>(jackson)
}

/**
 * Adds converter for [ZoneOffset] type.
 *
 * @param jackson object mapper that performs actual serialization and deserialization
 */
fun DataConversion.Configuration.convertZoneOffset(jackson: ObjectMapper = jackson()) {
    convert<ZoneOffset> {
            decode { values, _ ->
                values.singleOrNull()?.let { jackson.readValue("\"$it\"") }
            }

            encode { value ->
                when (value) {
                    null -> listOf()
                    is ZoneOffset -> {
                        listOf(jackson.writeValueAsString(value).removeSurrounding("\"")) // we want string-encoding, not json-encoding
                    }
                    else -> throw DataConversionException("Cannot convert $value : ${value.javaClass.simpleName} is not ${ZoneOffset::class.simpleName}")
                }
            }
        }
}

/**
 * Adds converter for [ZoneId] type.
 *
 * @param jackson object mapper that performs actual serialization and deserialization
 */
fun DataConversion.Configuration.convertZoneId(jackson: ObjectMapper = jackson()) {
    convert<ZoneId>(jackson)
}

/**
 * Adds converter for [YearMonth] type.
 *
 * @param jackson object mapper that performs actual serialization and deserialization
 */
fun DataConversion.Configuration.convertYearMonth(jackson: ObjectMapper = jackson()) {
    convert<YearMonth>(jackson)
}

/**
 * Adds converter for [Year] type.
 *
 * @param jackson object mapper that performs actual serialization and deserialization
 */
fun DataConversion.Configuration.convertYear(jackson: ObjectMapper = jackson()) {
    convert<Year>(jackson)
}

/**
 * Adds converter for [Period] type.
 *
 * @param jackson object mapper that performs actual serialization and deserialization
 */
fun DataConversion.Configuration.convertPeriod(jackson: ObjectMapper = jackson()) {
    convert<Period>(jackson)
}

/**
 * Adds converter for [OffsetTime] type.
 *
 * @param jackson object mapper that performs actual serialization and deserialization
 */
fun DataConversion.Configuration.convertOffsetTime(jackson: ObjectMapper = jackson()) {
    convert<OffsetTime>(jackson)
}

/**
 * Adds converter for [MonthDay] type.
 *
 * @param jackson object mapper that performs actual serialization and deserialization
 */
fun DataConversion.Configuration.convertMonthDay(jackson: ObjectMapper = jackson()) {
    convert<MonthDay>(jackson)
}

/**
 * Adds converter for [LocalTime] type.
 *
 * @param jackson object mapper that performs actual serialization and deserialization
 */
fun DataConversion.Configuration.convertLocalTime(jackson: ObjectMapper = jackson()) {
    convert<LocalTime>(jackson)
}

/**
 * Adds converter for [LocalDate] type.
 *
 * @param jackson object mapper that performs actual serialization and deserialization
 */
fun DataConversion.Configuration.convertLocalDate(jackson: ObjectMapper = jackson()) {
    convert<LocalDate>(jackson)
}

/**
 * Adds converter for [LocalDateTime] type.
 *
 * @param jackson object mapper that performs actual serialization and deserialization
 */
fun DataConversion.Configuration.convertLocalDateTime(jackson: ObjectMapper = jackson()) {
    convert<LocalDateTime>(jackson)
}

/**
 * Adds converter for [Duration] type.
 *
 * @param jackson object mapper that performs actual serialization and deserialization
 */
fun DataConversion.Configuration.convertDuration(jackson: ObjectMapper = jackson()) {
    convert<Duration>(jackson)
}

/**
 * Adds converter for [ZonedDateTime] type.
 *
 * @param jackson object mapper that performs actual serialization and deserialization
 */
fun DataConversion.Configuration.convertZonedDateTime(jackson: ObjectMapper = jackson()) {
    convert<ZonedDateTime>(jackson)
}

/**
 * Adds converter for [OffsetDateTime] type.
 *
 * @param jackson object mapper that performs actual serialization and deserialization
 */
fun DataConversion.Configuration.convertOffsetDateTime(jackson: ObjectMapper = jackson()) {
    convert<OffsetDateTime>(jackson)
}

/**
 * Adds converter for [Instant] type.
 *
 * @param jackson object mapper that performs actual serialization and deserialization
 */
fun DataConversion.Configuration.convertInstant(jackson: ObjectMapper = jackson()) {
    convert<Instant>(jackson)
}
