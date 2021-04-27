package com.jrk.mood4food.model.api.endpoints

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.jrk.mood4food.model.api.Backend
import com.jrk.mood4food.model.api.Endpoint
import com.jrk.mood4food.model.api.Endpoints
import com.jrk.mood4food.model.api.entity.Recipe
import kotlin.reflect.KFunction1

object RecipeEndpoint: Endpoint {

    private val endpoint = Endpoints.RECIPES

    // Get all recipes
    fun getAll(context: Context, callback: KFunction1<List<Any>, Unit>, limit: Int = 50, offset: Int = 0){
        Backend.getArray(context, endpoint, mapOf("limit" to "" + limit, "offset" to "" + offset), ::doArrayCallback, callback)
    }

    // Get one recipe
    fun get(context: Context, callback: KFunction1<Any, Unit>, recipeId: Int){
        Backend.get(context, endpoint, mapOf("recipeId" to "" + recipeId), ::doCallback, callback)
    }

    // Search for recipes
    fun search(context: Context, callback: KFunction1<List<Any>, Unit>, query: String, limit: Int = 50, offset: Int = 0){
        Backend.getArray(context, endpoint, mapOf("search" to query, "limit" to "" + limit, "offset" to "" + offset), ::doArrayCallback, callback)
    }

    // Get recipes by ingredient rating
    fun getByRating(context: Context, callback: KFunction1<List<Any>, Unit>, likes: List<String>, dislikes: List<String>, limit: Int = 50, offset: Int = 0){
        val like = likes.joinToString(separator = ",")
        val dislike = dislikes.joinToString(separator = ",")
        Backend.getArray(context, endpoint, mapOf("like" to like, "dislike" to dislike, "limit" to "" + limit, "offset" to "" + offset), ::doArrayCallback, callback)
    }

    override fun convert(jsonObject: JsonObject): Recipe {
        val gson = GsonBuilder().create()
        return gson.fromJson(jsonObject, Recipe::class.java)
    }
    override fun convertArray(jsonArray: JsonArray): List<Recipe> {
        val gson = GsonBuilder().create()
        return gson.fromJson(jsonArray, Array<Recipe>::class.java).toList()
    }

}
