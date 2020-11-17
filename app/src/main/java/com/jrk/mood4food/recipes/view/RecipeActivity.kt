package com.jrk.mood4food.recipes.view

import android.app.AlertDialog
import android.content.DialogInterface

import android.os.Bundle
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.john.waveview.WaveView
import com.jrk.mood4food.*
import com.jrk.mood4food.recipes.controller.RecipeControler
import com.jrk.mood4food.recipes.model.RecipeObserver
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.recipes.model.RecipeEntity


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

}