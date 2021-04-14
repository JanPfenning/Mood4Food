package com.jrk.mood4food.recipes.detail.model

import com.jrk.mood4food.App
import com.jrk.mood4food.model.localStorage.LocalStorage
import com.jrk.mood4food.recipes.add_mod.Ingredient
import com.jrk.mood4food.recipes.add_mod.Material

class RecipeRepository{
    fun loadRecipeDetails(id:String):RecipeEntity{
        var recipe = RecipeEntity(App.getContext())
        //recipe.loadFromLocalStorage(id,recipe) //TODO
        return recipe
    }
    fun storeRecipe(recipe:RecipeEntity){
        //recipe.saveToLocalStorage(recipe) //TODO
    }
    fun loadAllRecipes(): Set<RecipeEntity> {
        var recipes = LocalStorage.getAll(App.getContext(),RecipeEntity::class.java)
        return recipes.toSet() as Set<RecipeEntity>
    }

    fun setToIngredient(set: Set<Set<String>>): MutableSet<Ingredient> {
        val retSet = mutableSetOf<Ingredient>()
        set.forEach{e ->
            var i = Ingredient()
            i.name = e.elementAt(0)
            i.amount = e.elementAt(1)
            retSet.add(i)
        }
        return retSet
    }

    fun setToMaterials(set: Set<String>): MutableSet<Material> {
        val retSet = mutableSetOf<Material>()
        set.forEach{e ->
            var i = Material()
            i.name = e
            retSet.add(i)
        }
        return retSet
    }

    fun ingToMutableSet(set: Set<Ingredient>): MutableSet<Set<String>> {
        val retSet = mutableSetOf<Set<String>>()
        set.forEach{e ->
            if(!e.name.equals("") && !e.amount.equals("")) {
                retSet.add(setOf(e.name, e.amount))
            }
        }
        return retSet
    }

    fun matToMutableSet(set: Set<Material>): MutableSet<String> {
        val retSet = mutableSetOf<String>()
        set.forEach{e ->
            if(!e.name.equals("")) {
                retSet.add(e.name)
            }
        }
        return retSet
    }

    fun removeRecipe(recipe: RecipeEntity) {
        //recipe.removeFromLocalStorage();
    }
}