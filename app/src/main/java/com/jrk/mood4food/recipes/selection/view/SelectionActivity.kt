package com.jrk.mood4food.recipes.selection.view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jrk.mood4food.App
import com.jrk.mood4food.NavBarActivity
import com.jrk.mood4food.R
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.model.api.endpoints.RecipeEndpoint
import com.jrk.mood4food.model.api.entity.Recipe
import com.jrk.mood4food.recipes.add_mod.view.Add_ModActivity
import com.jrk.mood4food.recipes.detail.model.RecipeEntity
import com.jrk.mood4food.recipes.detail.view.DetailActivity
import com.jrk.mood4food.recipes.selection.RecipeAdapter
import com.jrk.mood4food.recipes.selection.controller.SelectionController
import com.jrk.mood4food.recipes.selection.model.SelectionObserver
import kotlin.reflect.KFunction1

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

        //Add Search functionality
        findViewById<ImageView>(R.id.searchRecipe).setOnClickListener{
            // TODO open Input field
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

    private fun showRecipeLists(recipes: Array<RecipeEntity>) {
        //TODO Show all Recipes coming from API based on Searchresult
        //Fill Cloud recipes recycler
        val recommendedView: RecyclerView = findViewById(R.id.recommended_recipes_recycler)
        recommendedView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val recommendedAdapter = RecipeAdapter(recipes, this)
        recommendedView.adapter = recommendedAdapter

        //Fill Favorite recipes recycler
        val favoriteView: RecyclerView = findViewById(R.id.favorite_recipes_recycler)
        favoriteView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        // TODO filter for recipes that have "recipe.favorite = true"
        val favs = recipes.filter { recipeEntity -> recipeEntity.favorite }
        val favoriteAdapter = RecipeAdapter(favs.toTypedArray(),this)
        favoriteView.adapter = favoriteAdapter

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

    //Called when users sends the query string
    fun onApiSearch(){
        RecipeEndpoint.getAll(App.getContext(), this::onApiResult as KFunction1<List<Any>, Unit>)
    }
    //Called when async backend call arrives
    fun onApiResult(recipes: List<Recipe>){

    }
}

interface RecipeClickListener {
    fun onRecipeClickListener(id:String)
}