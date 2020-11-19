package com.jrk.mood4food.recipes.add_mod.view

import android.os.Bundle
import android.widget.ImageView
import com.jrk.mood4food.*
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.recipes.add_mod.controller.Add_ModController
import com.jrk.mood4food.recipes.add_mod.model.Add_ModObserver


class Add_ModActivity : NavBarActivity(), Add_ModView, Add_ModObserver {
    private val model = ModelModule.dataAccessLayer
    private val controller = Add_ModController(model)

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_edit_recipe)
        super.onCreate(savedInstanceState)
        controller.bind(this)

        //TODO Functionality to save recipe
        findViewById<ImageView>(R.id.confirm).setOnClickListener{
            //controller.onSave(title,ingredients,...)
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

    override fun recipeSaved() {
        //TODO what happens after recipe has been saved
    }

}