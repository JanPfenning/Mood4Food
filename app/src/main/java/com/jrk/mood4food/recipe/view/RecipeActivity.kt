package com.jrk.mood4food.recipe.view

import android.os.Bundle
import com.jrk.mood4food.*
import com.jrk.mood4food.recipe.controller.RecipeControler
import com.jrk.mood4food.recipe.model.RecipeObserver
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.recipe.model.RecipeEntity


class RecipeActivity : NavBarActivity(), RecipeView, RecipeObserver {
    private val model = ModelModule.dataAccessLayer
    private val controller = RecipeControler(model)

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_read_recipe)
        super.onCreate(savedInstanceState)
        controller.bind(this)

    }

    override fun onStart() {
        super.onStart()
        model.register(this)
    }

    override fun onStop() {
        super.onStop()
        model.unregister(this)
    }

    override fun getRecipe(x: RecipeEntity) {
        TODO("Not yet implemented")
    }

    override fun recipeStoredIn() {
        TODO("Not yet implemented")
    }

}