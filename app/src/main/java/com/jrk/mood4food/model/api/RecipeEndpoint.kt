package com.jrk.mood4food.model.api

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.jrk.mood4food.recipes.detail.model.RecipeEntity
import org.json.JSONException

object RecipeEndpoint {

    fun getAll(context: Context): List<RecipeEntity>{
        Log.d("APICON", "test")

        val recipes = ArrayList<RecipeEntity>()

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = "http://irowall-tactical.com/mood4food/api/recipes/"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonArrayRequest(Request.Method.GET, url, null, Response.Listener { response ->
            try {
                for (i in 0 until response.length()) {

                    val jsonObject = response.getJSONObject(i)

                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { error -> error.printStackTrace() })

        queue.add(jsonObjectRequest)

        return recipes
    }

}