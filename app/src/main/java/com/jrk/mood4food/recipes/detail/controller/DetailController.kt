package com.jrk.mood4food.recipes.detail.controller

import com.jrk.mood4food.model.DataAccessLayer
import com.jrk.mood4food.recipes.detail.model.RecipeEntity
import com.jrk.mood4food.recipes.detail.view.DetailView

class DetailController(private val model: DataAccessLayer) {
    private  lateinit var view: DetailView
    fun bind(detailView: DetailView) {
        view = detailView
    }
    fun updateFav(recipe: RecipeEntity){
        model.saveRecipe(recipe)
    }
}