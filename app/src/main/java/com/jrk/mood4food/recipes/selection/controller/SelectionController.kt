package com.jrk.mood4food.recipes.selection.controller

import com.jrk.mood4food.App
import com.jrk.mood4food.model.DataAccessLayer
import com.jrk.mood4food.model.api.entity.Recipe
import com.jrk.mood4food.recipes.detail.model.RecipeEntity
import com.jrk.mood4food.recipes.selection.view.SelectionView

class SelectionController(private val model: DataAccessLayer) {
    private  lateinit var view: SelectionView
    fun bind(selectionView: SelectionView) {
        view = selectionView
    }

    fun toRecipeEntity(it: Recipe): RecipeEntity {
        val recipeEntity: RecipeEntity = RecipeEntity(App.getContext());
        recipeEntity.title = it.title
        recipeEntity.APIid = it.recipeId
        return recipeEntity;
    }
}