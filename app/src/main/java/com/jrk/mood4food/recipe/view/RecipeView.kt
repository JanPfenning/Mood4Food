package com.jrk.mood4food.recipe.view

import com.jrk.mood4food.recipe.model.RecipeEntity

interface RecipeView {
    fun getRecipe(x:RecipeEntity)
    //fun getRecipes(x:List<RecipeEntity>)
}