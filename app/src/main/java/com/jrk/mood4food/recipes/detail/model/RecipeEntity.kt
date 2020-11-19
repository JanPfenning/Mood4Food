package com.jrk.mood4food.recipes.detail.model

import android.content.Context
import com.jrk.mood4food.Tuple
import com.jrk.mood4food.model.localStorage.LocalEntity

class RecipeEntity(context: Context) : LocalEntity(context, RecipeEntity::class.java, true) {

    var title = ""
    var ingredients:Set<Set<String>> = emptySet()
    var materials:Set<String> = emptySet()
    var description = ""
    var lastEaten = ""

}