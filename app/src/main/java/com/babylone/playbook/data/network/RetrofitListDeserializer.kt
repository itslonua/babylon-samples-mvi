package com.babylone.playbook.data.network

import android.support.annotation.NonNull
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.*


internal class RetrofitListDeserializer : JsonDeserializer<List<*>> {


    @Throws(JsonParseException::class)
    override fun deserialize(
        @NonNull json: JsonElement, @NonNull type: Type,
        @NonNull context: JsonDeserializationContext
    ): List<*> {

        val elements = json.asJsonArray
        val result = ArrayList<Any>(elements.size())
        val elementType = (type as ParameterizedType).actualTypeArguments[0]

        for (element in elements) {
            var current: Any? = null

            try {
                current = context.deserialize<Any>(element, elementType)
            } catch (e: JsonParseException) {
                e.printStackTrace()
            }

            current?.let {
                result.add(it)
            }
        }
        return result
    }

}