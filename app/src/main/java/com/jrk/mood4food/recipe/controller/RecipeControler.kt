package com.jrk.mood4food.recipe.controller

import com.jrk.mood4food.model.DataAccessLayer
import com.jrk.mood4food.recipe.model.RecipeEntity
import com.jrk.mood4food.recipe.view.RecipeView

class RecipeControler(private val model: DataAccessLayer) {
    private  lateinit var view: RecipeView
    fun bind(recipeView: RecipeView) {
        view = recipeView
    }

    fun onRecipeAdd(recipe: RecipeEntity) {
        //model.performRecipeAdd()
    }
}