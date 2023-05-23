package com.proyecto404.backoffice.modules.common.base.serialization.gson

import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.nbottarini.asimov.time.YearMonthParser
import com.nbottarini.asimov.time.formatAsISO8601
import com.proyecto404.backoffice.modules.common.base.domain.Id
import com.proyecto404.backoffice.modules.common.base.serialization.Serializer
import com.proyecto404.backoffice.modules.common.base.serialization.gson.kotlinReflective.KotlinReflectiveTypeAdapterFactory
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth

class GsonSerializer: Serializer {
    private val builder: GsonBuilder = GsonBuilder()
    private val yearMonthParser by lazy { YearMonthParser() }

    init {
        builder.registerTypeAdapterFactory(KotlinReflectiveTypeAdapterFactory.create())
        builder.registerTypeAdapter(Id::class.java, IdValueSerializer())
        builder.registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeSerializer())
        builder.registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())
        builder.registerTypeAdapter(LocalTime::class.java, LocalTimeSerializer())
        builder.registerTypeAdapter(YearMonth::class.java, StringValueSerializer(
            { yearMonthParser.parseISO8601(it) },
            { it.formatAsISO8601() }
        ))
    }

    fun registerTypeAdapter(type: Class<*>, adapter: Any) {
        builder.registerTypeAdapter(type, adapter)
    }

    inline fun <reified T> registerTypeAdapter(adapter: Any) {
        registerTypeAdapter(T::class.java, adapter)
    }

    fun registerTypeAdapterFactory(factory: TypeAdapterFactory) {
        builder.registerTypeAdapterFactory(factory)
    }

    override fun serialize(obj: Any?): String {
        return builder.create().toJson(obj)
    }

    override fun <T> deserialize(serialized: String?, type: Class<T>): T {
        return builder.create().fromJson(serialized, type)
    }

    fun <T> deserialize(serialized: String?, type: Type): T {
        return builder.create().fromJson(serialized, type)
    }

    inline fun <reified T> deserialize(serialized: String?): T {
        return deserialize(serialized, T::class.java)
    }

    inline fun <reified T> deserializeList(serialized: String?): List<T> {
        val listType = object: TypeToken<ArrayList<T>>(){}.type
        return deserialize(serialized, listType)
    }

    inline fun <reified K, reified V> deserializeMap(serialized: String?): Map<K, V> {
        val mapType = object: TypeToken<Map<K, V>>(){}.type
        return deserialize(serialized, mapType)
    }
}
