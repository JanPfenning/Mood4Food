package com.jrk.mood4food.recipes.detail.view

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.jrk.mood4food.*
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.model.localStorage.LocalStorage
import com.jrk.mood4food.recipes.detail.controller.DetailController
import com.jrk.mood4food.recipes.detail.model.DetailObserver
import com.jrk.mood4food.recipes.detail.model.RecipeEntity


class DetailActivity : NavBarActivity(), DetailView, DetailObserver {
    private val model = ModelModule.dataAccessLayer
    private val controller = DetailController(model)

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_read_recipe)
        super.onCreate(savedInstanceState)
        controller.bind(this)

        //Testcode
        var testRecipe : RecipeEntity = RecipeEntity(App.getContext())
        testRecipe.title = "Test Rezept"
        testRecipe.ingredients = setOf("1","2","3")
        testRecipe.materials = setOf("1","2","3")
        testRecipe.description = "Schritt für Schritt Anleitung"
        testRecipe.saveToLocalStorage(testRecipe)
        var id = testRecipe.storageAddress //TODO this id should be given to the activity via parameter

        var recipe:RecipeEntity
        if (id !is String){
            Log.e("RECIPE","id is not there")
        }else{
            recipe = model.getRecipeRepository().loadRecipeDetails(id)
            findViewById<TextView>(R.id.recipe_title).text = recipe.title
        }
    }

    override fun onStart() {
        super.onStart()
        model.register(this)
    }


    override fun onStop() {
        super.onStop()
        model.unregister(this)
    }

}