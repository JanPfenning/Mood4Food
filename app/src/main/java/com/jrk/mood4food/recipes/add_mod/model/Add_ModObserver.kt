package com.jrk.mood4food.recipes.add_mod.model

import com.jrk.mood4food.model.DomainObservers
import com.jrk.mood4food.recipes.detail.model.RecipeEntity

interface Add_ModObserver: DomainObservers {
    fun recipeSaved(recipeEntity: RecipeEntity)
}