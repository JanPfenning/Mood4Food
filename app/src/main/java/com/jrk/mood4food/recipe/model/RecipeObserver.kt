package com.jrk.mood4food.recipe.model

import com.jrk.mood4food.model.DomainObservers

interface RecipeObserver: DomainObservers {
    fun recipeStoredIn()

}