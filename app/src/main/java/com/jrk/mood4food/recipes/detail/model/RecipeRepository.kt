package com.jrk.mood4food.recipes.detail.model

import com.jrk.mood4food.App
import com.jrk.mood4food.model.localStorage.LocalStorage

class RecipeRepository{
    fun loadRecipeDetails(id:String):RecipeEntity{
        var recipe = RecipeEntity(App.getContext())
        recipe.loadFromLocalStorage(id,recipe)
        return recipe
    }
    fun storeRecipe(recipe:RecipeEntity){
        recipe.saveToLocalStorage(recipe)
    }
    fun loadAllRecipes(): Set<RecipeEntity> {
        var recipes = LocalStorage.getAll(App.getContext(),RecipeEntity::class.java)
        return recipes.toSet() as Set<RecipeEntity>
    }
}