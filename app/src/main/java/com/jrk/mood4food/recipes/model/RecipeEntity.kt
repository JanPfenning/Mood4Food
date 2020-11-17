package com.jrk.mood4food.recipes.model

import android.content.Context
import com.jrk.mood4food.model.localStorage.LocalEntity

class RecipeEntity(context: Context) : LocalEntity(context, RecipeEntity::class.java, true) {

    var title = "";
    var ingredients:List<String> = mutableListOf()
    var materials:List<String> = mutableListOf()
    var description = "";
    var steps:List<String> = mutableListOf()

}