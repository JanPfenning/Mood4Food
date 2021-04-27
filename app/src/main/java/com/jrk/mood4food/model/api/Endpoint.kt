package com.jrk.mood4food.model.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlin.reflect.KFunction1

interface Endpoint {

    // Gson Conversion
    // Convert JSON to Java Object
    fun convert(jsonObject: JsonObject): Any
    fun convertArray(jsonArray: JsonArray): List<Any>

    // Callback Helper-Methods
    fun doArrayCallback(jsonArray: JsonArray, callback: KFunction1<List<Any>, Unit>) {
        callback(convertArray(jsonArray))
    }
    fun doCallback(jsonObject: JsonObject, callback: KFunction1<Any, Unit>) {
        callback(convert(jsonObject))
    }

}