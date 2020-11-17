package com.jrk.mood4food.recipe.model

import android.content.Context
import com.jrk.mood4food.App
import com.jrk.mood4food.model.localStorage.LocalStorage

class RecipeRepository {

    private fun newRecipeEntity(title:String, ingredients: List<String>, materials: List<String>, description: String, steps: List<String>) {
        val context: Context = App.getContext()
        val newEntity = RecipeEntity(context)
        newEntity.title = title;
        newEntity.ingredients = ingredients
        newEntity.materials = materials
        newEntity.description = description
        newEntity.steps = steps
        newEntity.saveToLocalStorage(newEntity)
    }

    public fun storeNewRecipe(title:String, ingredients: List<String>, materials: List<String>, description: String, steps: List<String>){
        newRecipeEntity(title,ingredients,materials,description,steps)
    }

    /**
     * @param(x:String) title of the recipe
     * @return(y:RecipeEntity?) entity of the found Recipe or null if none exists with that title
     *
     * */
    public fun getRecipeByName(x:String): RecipeEntity? {
        val entities = LocalStorage.getAll(App.getContext(), RecipeEntity::class.java) as List<RecipeEntity>
        entities.forEach {

            if (it.title == x) {
                return it
            }
        }
        return null
    }

}