package com.jrk.mood4food.recipes.selection.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
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

        //Load all recipes
        val recipes:Array<RecipeEntity> = model.getRecipeRepository().loadAllRecipes().toTypedArray()

        //Add recipe button
        findViewById<ImageView>(R.id.addRecipe).setOnClickListener{
            startActivity(Intent(this,Add_ModActivity::class.java))
        }

        showRecipeLists(recipes);
    }

    override fun onStart() {
        super.onStart()
        model.register(this)
    }


    override fun onStop() {
        super.onStop()
        model.unregister(this)
    }

    fun showRecipeLists(recipes:Array<RecipeEntity>){
        //TODO Filter recipes for every adapter
        //Fill Recommended recipes recycler
        val recommendedView:RecyclerView = findViewById(R.id.recommended_recipes_recycler)
        recommendedView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        val recommendedAdapter = RecipeAdapter(recipes,this)
        recommendedView.adapter = recommendedAdapter

        //Fill Recommended recipes recycler
        val favoriteView:RecyclerView = findViewById(R.id.favorite_recipes_recycler)
        favoriteView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        val favoriteAdapter = RecipeAdapter(recipes,this)
        favoriteView.adapter = favoriteAdapter

        //Fill Recommended recipes recycler
        val longagoView:RecyclerView = findViewById(R.id.longago_recipes_recycler)
        longagoView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        val longagoAdapter = RecipeAdapter(recipes,this)
        longagoView.adapter = longagoAdapter

        //Fill own recipes recycler
        val ownRecipesView:RecyclerView = findViewById(R.id.own_recipes_recycler)
        ownRecipesView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        val recipesAdapter = RecipeAdapter(recipes,this)
        ownRecipesView.adapter = recipesAdapter
    }

    //Forwarded listener
    override fun onRecipeClickListener(id: String) {
        //Log.i("JAN",id)
        val intent = Intent(this,DetailActivity::class.java)
        intent.putExtra("id",id)
        startActivity(intent)
    }

    fun newRecipe(){
        var recipe = RecipeEntity(App.getContext())
        recipe.title = "Testrezept"
        recipe.ingredients = setOf(setOf("Tomaten", "100g"),setOf("Pommes","1Kg"),setOf("Schintzel","1Stück"))
        recipe.saveToLocalStorage(recipe)
    }
}

interface RecipeClickListener {
    fun onRecipeClickListener(id:String)
}