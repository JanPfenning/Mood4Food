package com.jrk.mood4food.recipes.detail.model

import com.jrk.mood4food.App
import com.jrk.mood4food.model.localStorage.LocalStorage

class RecipeRepository{
    fun loadRecipeDetails(id:String):RecipeEntity{
        var recipe = RecipeEntity(App.getContext())

        LocalStorage.load(App.getContext(), id, recipe)
        return recipe
    }
    fun storeRecipe(recipe:RecipeEntity) {
        LocalStorage.save(App.getContext(), recipe)

    }
    fun loadAllRecipes(): Set<RecipeEntity> {
        var recipes = LocalStorage.getAll(App.getContext(),RecipeEntity::class.java)
        return recipes.toSet() as Set<RecipeEntity>
    }

    fun removeRecipe(recipe: RecipeEntity) {
        //recipe.removeFromLocalStorage();
    }
}