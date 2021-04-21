package com.jrk.mood4food.model.api

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import org.json.JSONException
import kotlin.reflect.KFunction1
import kotlin.reflect.KFunction2

object Backend {

    val PROTOCOL = "http"
    val DOMAIN = "irowall-tactical.com"
    val SERVICE_PATH = "/mood4food/api/"

    fun getArray(context: Context, endpoint: Endpoint, params: Map<String, String>, callback: KFunction2<JsonArray, KFunction1<List<Any>, Unit>, Unit>, endpointCallback: KFunction1<List<Any>, Unit>) {

        var objects = JsonArray()

        val queue = Volley.newRequestQueue(context)

        var query = ""
        params.entries.forEach {
            if(query == ""){
                query += "?";
            }else{
                query += "&";
            }
            query += it.key + "=" + it.value
        }

        Log.e("APICON", "test")

        val url = PROTOCOL + "://" + DOMAIN +  SERVICE_PATH + endpoint.path + query

        val jsonObjectRequest = JsonArrayRequest(Request.Method.GET, url, null, Response.Listener { response ->
            try {
                objects = JsonParser.parseString(response.toString()).asJsonArray
                Log.e("APICON", objects.toString())
            } catch (e: JSONException) {
                e.printStackTrace()
                Log.e("APICON", e.toString())
            } finally {
                callback(objects, endpointCallback)
            }
        }, Response.ErrorListener {
            error -> error.printStackTrace()
            Log.e("APICON", error.toString())
            callback(objects, endpointCallback)
        })

        queue.add(jsonObjectRequest)

    }

}
