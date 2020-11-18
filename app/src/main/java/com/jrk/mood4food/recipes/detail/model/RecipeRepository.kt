package com.jrk.mood4food.recipes.detail.model

import android.content.Context
import com.jrk.mood4food.App
import com.jrk.mood4food.model.localStorage.LocalStorage

class RecipeRepository{
    fun loadRecipeDetails(id:String):RecipeEntity{
        var recipe = RecipeEntity(App.getContext())
        recipe.loadFromLocalStorage(id,recipe)
        return recipe
    }
}