package com.jrk.mood4food.model.api.endpoints

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.jrk.mood4food.model.api.Backend
import com.jrk.mood4food.model.api.Endpoint
import com.jrk.mood4food.model.api.Endpoints
import com.jrk.mood4food.model.api.entity.Ingredient
import kotlin.reflect.KFunction1

object IngredientEndpoint: Endpoint {

    private val endpoint = Endpoints.INGREDIENTS

    // Get all ingredients
    fun getAll(context: Context, callback: KFunction1<List<Any>, Unit>, limit: Int = 50, offset: Int = 0){
        Backend.getArray(context, endpoint, mapOf("limit" to "" + limit, "offset" to "" + offset), ::doArrayCallback, callback)
    }

    override fun convert(jsonObject: JsonObject): Ingredient {
        val gson = GsonBuilder().create()
        return gson.fromJson(jsonObject, Ingredient::class.java)
    }
    override fun convertArray(jsonArray: JsonArray): List<Ingredient> {
        val gson = GsonBuilder().create()
        return gson.fromJson(jsonArray, Array<Ingredient>::class.java).toList()
    }

}