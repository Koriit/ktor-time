package korrit.kotlin.ktor

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.features.DataConversion
import io.ktor.util.DataConversionException

/**
 *
 */
internal inline fun <reified T> DataConversion.Configuration.convert(jackson: ObjectMapper) {
    convert<T> {
        decode { values, _ ->
            values.singleOrNull()?.let { jackson.readValue<T>("\"$it\"") }
        }

        encode { value ->
            when (value) {
                null -> listOf()
                is T -> {
                    listOf(jackson.writeValueAsString(value).removeSurrounding("\"")) // we want string-encoding, not json-encoding
                }
                else -> throw DataConversionException("Cannot convert $value : ${value.javaClass.simpleName} is not ${T::class.simpleName}")
            }
        }
    }
}

/**
 * Build a default Jackson instance if none provided by the user.
 */
internal fun jackson() = JsonMapper.builder()
    .addModule(JavaTimeModule())
    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    .build()
