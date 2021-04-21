package com.jrk.mood4food.model.api

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.jrk.mood4food.model.api.entity.Recipe
import com.jrk.mood4food.recipes.detail.model.RecipeEntity
import kotlin.reflect.KFunction1

object RecipeEndpoint{

    private val endpoint = Endpoint.RECIPES
    private val gson = GsonBuilder().create()

    fun getAll(context: Context, callback: KFunction1<List<Any>, Unit>){
        Backend.getArray(context, endpoint, mapOf(), ::getAllCallback, callback)
    }
    fun getAllCallback(jsonArray: JsonArray, callback: KFunction1<List<Any>, Unit>) {
        callback(convertArray(jsonArray))
    }

    fun get(context: Context, recipeId: String): List<Recipe>{

        val recipes = ArrayList<Recipe>()


        return recipes
    }

    private fun convert(jsonObject: JsonObject) {
        TODO("Not yet implemented")
    }

    private fun convertArray(jsonArray: JsonArray): List<Recipe> {
        Log.e("APICON", "ee" + jsonArray.toString())
        return gson.fromJson(jsonArray, Array<Recipe>::class.java).toList()
    }

}
