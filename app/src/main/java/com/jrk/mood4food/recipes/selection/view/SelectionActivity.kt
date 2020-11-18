package com.jrk.mood4food.recipes.selection.view

import android.os.Bundle
import com.jrk.mood4food.*
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.model.localStorage.LocalStorage
import com.jrk.mood4food.recipes.detail.model.RecipeEntity

class SelectionActivity : NavBarActivity(), SelectionView, SelectionObserver {
    private val model = ModelModule.dataAccessLayer
    private val controller = SelectionController(model)

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_read_recipe)
        super.onCreate(savedInstanceState)
        controller.bind(this)

        LocalStorage.getAll(App.getContext(), RecipeEntity::class.java)

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