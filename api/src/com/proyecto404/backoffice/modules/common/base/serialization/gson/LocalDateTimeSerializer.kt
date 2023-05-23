package com.proyecto404.backoffice.modules.common.base.serialization.gson

import com.google.gson.*
import com.nbottarini.asimov.time.LocalDateTimeParser
import com.nbottarini.asimov.time.formatAsISO8601
import java.lang.reflect.Type
import java.time.LocalDateTime

class LocalDateTimeSerializer: JsonSerializer<LocalDateTime?>, JsonDeserializer<LocalDateTime?> {
    override fun serialize(localDateTime: LocalDateTime?, srcType: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(localDateTime?.formatAsISO8601())
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): LocalDateTime {
        return LocalDateTimeParser().parseISO8601(json.asString)
    }
}
