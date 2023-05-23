package com.proyecto404.backoffice.base.serialization.gson

import com.google.gson.*
import com.nbottarini.asimov.time.LocalDateParser
import com.nbottarini.asimov.time.formatAsISO8601
import java.lang.reflect.Type
import java.time.LocalDate

class LocalDateSerializer: JsonSerializer<LocalDate?>, JsonDeserializer<LocalDate?> {
    override fun serialize(localDate: LocalDate?, srcType: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(localDate?.formatAsISO8601())
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): LocalDate {
        return LocalDateParser().parseISO8601(json.asString)
    }
}
