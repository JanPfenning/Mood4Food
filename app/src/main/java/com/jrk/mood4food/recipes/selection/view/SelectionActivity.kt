package com.jrk.mood4food.recipes.selection.view

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jrk.mood4food.App
import com.jrk.mood4food.NavBarActivity
import com.jrk.mood4food.R
import com.jrk.mood4food.R.id.recipe_selection_header
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

    private var API_result : Array<RecipeEntity> = emptyArray()

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_recipes)
        super.onCreate(savedInstanceState)
        controller.bind(this)

        //Load all local recipes
        val recipes:Array<RecipeEntity> = model.getRecipeRepository().loadAllRecipes().toTypedArray()

        //Add recipe button
        val addRecipeButton = findViewById<ImageView>(R.id.addRecipe)
        addRecipeButton.setOnClickListener{
            startActivity(Intent(this,Add_ModActivity::class.java))
        }

        val searchBar = findViewById<EditText>(R.id.search_field);
        val searchIcon = findViewById<ImageView>(R.id.searchRecipe);
        val commitSearch = findViewById<ImageView>(R.id.commitSearch)
        val closeSearch = findViewById<ImageView>(R.id.cancelSearch)
        //Add Search functionality
        searchBar.setOnKeyListener { v, keyCode, event ->
            when {
                //Check if it is the Enter-Key,      Check if the Enter Key was pressed down
                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                    //perform an action here e.g. a send message button click
                    commitSearch.performClick()
                    //return true
                    return@setOnKeyListener true
                }
                else -> false
            }
        };

        //Show Searchbar
        searchIcon.setOnClickListener{
            val header = findViewById<TextView>(recipe_selection_header)
            searchBar.visibility = View.VISIBLE;
            addRecipeButton.visibility = View.GONE
            header.visibility = View.GONE
            searchIcon.visibility = View.GONE;
            commitSearch.visibility = View.VISIBLE;
            closeSearch.visibility = View.VISIBLE;
        }

        //Commit Search
        commitSearch.setOnClickListener{
            onApiSearch(searchBar.text.toString())
            closeSearch.performClick()
        }

        //Cancel Search
        closeSearch.setOnClickListener{
            val header = findViewById<TextView>(recipe_selection_header)
            searchBar.text.clear()
            searchBar.visibility = View.GONE;
            addRecipeButton.visibility = View.VISIBLE
            header.visibility = View.VISIBLE
            searchIcon.visibility = View.VISIBLE;
            commitSearch.visibility = View.GONE;
            closeSearch.visibility = View.GONE;
        }

        showFavRecipes(recipes)
        showAllRecipes(recipes)
        getApi()
    }

    override fun onStart() {
        super.onStart()
        model.register(this)
    }


    override fun onStop() {
        super.onStop()
        model.unregister(this)
    }

    private fun showFavRecipes(recipes: Array<RecipeEntity>){
        //Fill Favorite recipes recycler
        // TODO filter based on search
        val favoriteView: RecyclerView = findViewById(R.id.favorite_recipes_recycler)
        favoriteView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val favs = recipes.filter { recipeEntity -> recipeEntity.favorite }
        val favoriteAdapter = RecipeAdapter(favs.toTypedArray(), this, false, this@SelectionActivity)
        favoriteView.adapter = favoriteAdapter
    }

    private fun showAllRecipes(recipes: Array<RecipeEntity>){
        //Fill own recipes recycler
        // TODO filter based on search
        val ownRecipesView:RecyclerView = findViewById(R.id.own_recipes_recycler)
        ownRecipesView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        val recipesAdapter = RecipeAdapter(recipes, this, false, this@SelectionActivity)
        ownRecipesView.adapter = recipesAdapter
    }

    private fun showAPIRecipes() {
        // TODO Show all Recipes coming from API
        //Fill Cloud recipes recycler
        val apiRecipeView: RecyclerView = findViewById(R.id.recommended_recipes_recycler)
        apiRecipeView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val apiRecipeAdapter = RecipeAdapter(this.API_result, this, true, this@SelectionActivity)
        apiRecipeView.adapter = apiRecipeAdapter
    }

    //Forwarded listener
    override fun onRecipeClickListener(id: String, api: Boolean) {
        //Log.i("JAN",id)
        val intent = Intent(this,DetailActivity::class.java)
        intent.putExtra("id",id)
        intent.putExtra("api", api)
        startActivity(intent)
    }

    //Called when users sends the query string
    fun onApiSearch(query: String){
        RecipeEndpoint.search(App.getContext(), this::onApiResult as KFunction1<List<Any>, Unit>, query, 20)
    }

    fun getApi(){
        RecipeEndpoint.getAll(App.getContext(), this::onApiResult as KFunction1<List<Any>, Unit>, 20)
    }

    //Called when async backend call arrives
    fun onApiResult(recipes: List<Recipe>){
        val translateList = mutableListOf<RecipeEntity>()
        recipes.forEach {
            translateList.add(controller.toRecipeEntity(it))
        }
        this.API_result = translateList.toTypedArray();
        showAPIRecipes()
    }
}

interface RecipeClickListener {
    fun onRecipeClickListener(id:String, api: Boolean)
}