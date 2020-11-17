package com.jrk.mood4food.recipes.model

import android.content.Context
import com.jrk.mood4food.App
import com.jrk.mood4food.model.localStorage.LocalStorage
import java.text.SimpleDateFormat

class RecipeRepository {

    private fun newRecipeEntity(title:String, ingredients: List<String>, materials: List<String>, description: String, steps: List<String>) {
        val context: Context = App.getContext()
        val newEntity = RecipeEntity(context)
        newEntity.title = title;
        newEntity.ingredients = ingredients
        newEntity.materials = materials
        newEntity.description = description
        newEntity.steps = steps
        newEntity.saveToLocalStorage(newEntity)
    }

}