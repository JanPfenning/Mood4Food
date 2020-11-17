package com.jrk.mood4food.recipes.controller

import com.jrk.mood4food.model.DataAccessLayer
import com.jrk.mood4food.recipes.view.RecipeView

class RecipeControler(private val model: DataAccessLayer) {
    private  lateinit var view: RecipeView
    fun bind(recipeView: RecipeView) {
        view = recipeView
    }

    /*
    fun onRecipeAdd(recipeAdd: Recipe) {
        model.performWaterAdd(view.getWaterAdd())
    }
    */


}