package com.jrk.mood4food.model.api

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.jrk.mood4food.model.api.entity.Recipe
import kotlin.reflect.KFunction1

object RecipeEndpoint{

    private val endpoint = Endpoint.RECIPES
    private val gson = GsonBuilder().create()

    // Get all recipes
    fun getAll(context: Context, callback: KFunction1<List<Any>, Unit>){
        Backend.getArray(context, endpoint, mapOf(), ::getAllCallback, callback)
    }
    fun getAllCallback(jsonArray: JsonArray, callback: KFunction1<List<Any>, Unit>) {
        callback(convertArray(jsonArray))
    }

    // Get one recipe
    fun get(context: Context, recipeId: Int, callback: KFunction1<Any, Unit>){
        Backend.get(context, endpoint, mapOf("recipeId" to "" + recipeId), ::getCallback, callback)
    }
    fun getCallback(jsonObject: JsonObject, callback: KFunction1<Any, Unit>) {
        callback(convert(jsonObject))
    }

    // Gson Conversion
    // Convert JSON to Java Object
    private fun convert(jsonObject: JsonObject): Recipe {
        return gson.fromJson(jsonObject, Recipe::class.java)
    }
    private fun convertArray(jsonArray: JsonArray): List<Recipe> {
        return gson.fromJson(jsonArray, Array<Recipe>::class.java).toList()
    }

}
