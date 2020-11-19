package com.jrk.mood4food.recipes.selection.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jrk.mood4food.App
import com.jrk.mood4food.NavBarActivity
import com.jrk.mood4food.R
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.recipes.add_mod.view.Add_ModActivity
import com.jrk.mood4food.recipes.detail.model.RecipeEntity
import com.jrk.mood4food.recipes.detail.view.DetailActivity
import com.jrk.mood4food.recipes.selection.RecipeAdapter
import com.jrk.mood4food.recipes.selection.controller.SelectionController
import com.jrk.mood4food.recipes.selection.model.SelectionObserver

class SelectionActivity : NavBarActivity(), SelectionView, SelectionObserver, RecipeClickListener {
    private val model = ModelModule.dataAccessLayer
    private val controller = SelectionController(model)

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_recipes)
        super.onCreate(savedInstanceState)
        controller.bind(this)

        val recipes:Array<RecipeEntity> = model.getRecipeRepository().loadAllRecipes().toTypedArray()

        findViewById<ImageView>(R.id.addRecipe).setOnClickListener{
            startActivity(Intent(this,Add_ModActivity::class.java))
        }

        val recyclerView:RecyclerView = findViewById(R.id.own_recipes_recycler);
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        val adapter = RecipeAdapter(recipes,this)

        //TODO make adapter clickable to open detail view
        recyclerView.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        model.register(this)
    }


    override fun onStop() {
        super.onStop()
        model.unregister(this)
    }

    override fun onRecipeClickListener(id: String) {
        Log.i("JAN",id)
    }
}

interface RecipeClickListener {
    fun onRecipeClickListener(id:String)
}