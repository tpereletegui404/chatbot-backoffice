package com.proyecto404.backoffice.base.serialization.gson

import com.google.gson.*
import com.proyecto404.backoffice.base.domain.Id
import java.lang.reflect.Type

class IdValueSerializer: JsonSerializer<Id<*>>, JsonDeserializer<Id<*>> {
    override fun serialize(value: Id<*>, srcType: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(value.toInt())
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): Id<*> {
        return Id<Unit>(json.asInt)
    }
}
