package com.jrk.mood4food.recipes.add_mod.controller

import com.jrk.mood4food.model.DataAccessLayer
import com.jrk.mood4food.recipes.add_mod.view.Add_ModView
import com.jrk.mood4food.recipes.detail.model.RecipeEntity

class Add_ModController(private val model: DataAccessLayer) {
    private  lateinit var view: Add_ModView
    fun bind(Add_ModView: Add_ModView) {
        view = Add_ModView
    }

    fun onSave(recipe: RecipeEntity){
        model.saveRecipe(recipe)
    }
}