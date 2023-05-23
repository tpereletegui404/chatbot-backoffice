package com.proyecto404.backoffice.base.infrastructure.serialization

import com.google.gson.reflect.TypeToken
import com.proyecto404.backoffice.base.serialization.Serializer
import com.proyecto404.backoffice.base.serialization.gson.GsonSerializer
import java.lang.reflect.Type

class JsonSerializer: Serializer {
    private val gsonSerializer = GsonSerializer()

    override fun serialize(obj: Any?) = gsonSerializer.serialize(obj)

    override fun <T> deserialize(serialized: String?, type: Class<T>) = gsonSerializer.deserialize(serialized, type)

    fun <T> deserialize(serialized: String?, type: Type) = gsonSerializer.deserialize<T>(serialized, type)

    inline fun <reified T> deserializeList(serialized: String?): List<T> {
        val listType = object: TypeToken<ArrayList<T>>(){}.type
        return deserialize(serialized, listType)
    }

    inline fun <reified T> deserializeSet(serialized: String?): Set<T> {
        return deserializeList<T>(serialized).toSet()
    }

    inline fun <reified K, reified V> deserializeMap(serialized: String?): Map<K, V> {
        val mapType = object: TypeToken<Map<K, V>>(){}.type
        return deserialize(serialized, mapType)
    }
}
