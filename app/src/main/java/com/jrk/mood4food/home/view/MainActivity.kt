package com.jrk.mood4food.home.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jrk.mood4food.App
import com.jrk.mood4food.NavBarActivity
import com.jrk.mood4food.R
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.model.api.endpoints.RecipeEndpoint
import com.jrk.mood4food.model.api.entity.Recipe
import com.jrk.mood4food.model.localStorage.LocalStorage
import com.jrk.mood4food.recipes.detail.model.RecipeEntity
import com.jrk.mood4food.recipes.detail.view.DetailActivity
import com.jrk.mood4food.recipes.selection.RecipeAdapter
import com.jrk.mood4food.recipes.selection.controller.SelectionController
import com.jrk.mood4food.recipes.selection.view.RecipeClickListener
import com.jrk.mood4food.waterbalance.model.IngredientsSettingsEntity
import kotlin.reflect.KFunction1

class MainActivity : NavBarActivity(), RecipeClickListener {

    private val model = ModelModule.dataAccessLayer
    private val controller = SelectionController(model)

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_home)
        super.onCreate(savedInstanceState)

        // Fill Water Balance Progress-Bar
        val waterBalance = model.getWaterRepository().getCurrentWaterBalancePercentage()
        val waterProgressBar = findViewById<ProgressBar>(R.id.homeWaterProgressBar)
        waterProgressBar.setProgress(waterBalance.toInt(), false)

        // Start loading the recipes from API asynchronously
        loadRecommendedRecipes()
    }

    fun loadRecommendedRecipes(){
        // Load recipes from API based on local liked / diskliked ingredients
        val likes = mutableListOf<String>()
        val dislikes = mutableListOf<String>()
        val ingredientsGood = model.getSettingsRepository().getGoodIngredients()
        ingredientsGood.forEach { likes.add(it.name) }
        val ingredientsBad = model.getSettingsRepository().getBadIngredients()
        ingredientsBad.forEach { likes.add(it.name) }

        RecipeEndpoint.getByRating(App.getContext(), this::onApiResult as KFunction1<List<Any>, Unit>, likes, dislikes, 10, 0)
    }

    private fun showRecommendedRecipes(recipes: Array<RecipeEntity>){
        // Fill recommended recipes recycler
        val recyclerView: RecyclerView = findViewById(R.id.rec_recommended_recipes)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val recipeAdapter = RecipeAdapter(recipes,this, true)
        recyclerView.adapter = recipeAdapter
    }

    // Called when async backend call arrives
    fun onApiResult(recipes: List<Recipe>){
        val translateList = mutableListOf<RecipeEntity>()
        recipes.forEach {
            translateList.add(controller.toRecipeEntity(it))
        }
        val result = translateList.toTypedArray();
        showRecommendedRecipes(result)
    }

    override fun onRecipeClickListener(id: String, api: Boolean) {
        val intent = Intent(this,DetailActivity::class.java)
        intent.putExtra("id",id)
        intent.putExtra("api", api)
        startActivity(intent)
    }

}
