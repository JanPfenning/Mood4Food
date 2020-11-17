package com.jrk.mood4food.recipes.view

import com.jrk.mood4food.recipes.model.RecipeEntity

interface RecipeView {
    fun getRecipe(x:RecipeEntity)
}