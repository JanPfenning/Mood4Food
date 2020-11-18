package com.jrk.mood4food.recipes.selection.view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import com.jrk.mood4food.*
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.model.localStorage.LocalStorage
import com.jrk.mood4food.recipes.add_mod.view.Add_ModActivity
import com.jrk.mood4food.recipes.detail.model.RecipeEntity
import com.jrk.mood4food.recipes.detail.model.RecipeRepository
import com.jrk.mood4food.recipes.selection.controller.SelectionController
import com.jrk.mood4food.recipes.selection.model.SelectionObserver

class SelectionActivity : NavBarActivity(), SelectionView, SelectionObserver {
    private val model = ModelModule.dataAccessLayer
    private val controller = SelectionController(model)

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_recipes)
        super.onCreate(savedInstanceState)
        controller.bind(this)

        //var recipes:List<RecipeEntity> = model.getRecipeRepository().loadAllRecipes() as List<RecipeEntity>

        findViewById<ImageView>(R.id.addRecipe).setOnClickListener{
            startActivity(Intent(this,Add_ModActivity::class.java))
        }
        //TODO fill the views with the recipe adapters
        /*
        * Here
        */

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