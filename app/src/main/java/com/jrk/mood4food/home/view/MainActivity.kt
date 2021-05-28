package com.jrk.mood4food.home.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jrk.mood4food.App
import com.jrk.mood4food.NavBarActivity
import com.jrk.mood4food.R
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.model.api.endpoints.RecipeEndpoint
import com.jrk.mood4food.model.api.entity.Recipe
import com.jrk.mood4food.onboarding.view.OnboardingActivity
import com.jrk.mood4food.recipes.detail.model.RecipeEntity
import com.jrk.mood4food.recipes.detail.view.DetailActivity
import com.jrk.mood4food.recipes.selection.RecipeAdapter
import com.jrk.mood4food.recipes.selection.controller.SelectionController
import com.jrk.mood4food.recipes.selection.view.RecipeClickListener
import com.jrk.mood4food.settings.model.IngredientType
import kotlin.reflect.KFunction1

class MainActivity : NavBarActivity(), RecipeClickListener {

    private val model = ModelModule.dataAccessLayer
    private val controller = SelectionController(model)

    private var limit = 10
    private var offset = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_home)
        super.onCreate(savedInstanceState)

        val onboardingFile = getSharedPreferences("onboarding", 0)
        if(onboardingFile.getBoolean("firstAppUsage", true)) {
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
            model.firstStartApp()
        }

        // Fill Water Balance Progress-Bar
        val waterBalance = model.getWaterRepository().getCurrentWaterBalancePercentage()
        val waterProgressBar = findViewById<ProgressBar>(R.id.homeWaterProgressBar)
        waterProgressBar.setProgress(waterBalance.toInt(), false)

        // Refresh logic
        val ivRefresh = findViewById<ImageView>(R.id.iv_refresh_recommendations)
        ivRefresh.setOnClickListener {
            offset += limit
            loadRecommendedRecipes()
        }

        // Start loading the recipes from API asynchronously
        loadRecommendedRecipes()
    }

    fun loadRecommendedRecipes(){
        Log.d("TEST", "load" + offset)
        // Load recipes from API based on local liked / diskliked ingredients
        val likes = mutableListOf<String>()
        val dislikes = mutableListOf<String>()
        val ingredientsGood = model.getSettingsRepository().getIngredients(IngredientType.Good)
        ingredientsGood.forEach { likes.add(it.name) }
        val ingredientsBad = model.getSettingsRepository().getIngredients(IngredientType.Bad)
        ingredientsBad.forEach { dislikes.add(it.name) }

        RecipeEndpoint.getByRating(App.getContext(), this::onApiResult as KFunction1<List<Any>, Unit>, likes, dislikes, limit, offset)
    }

    private fun showRecommendedRecipes(recipes: Array<RecipeEntity>){
        // Fill recommended recipes recycler
        val recyclerView: RecyclerView = findViewById(R.id.rec_recommended_recipes)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val recipeAdapter = RecipeAdapter(recipes,this, true, this@MainActivity)
        recyclerView.adapter = recipeAdapter
    }

    // Called when async backend call arrives
    fun onApiResult(recipes: List<Recipe>){
        Log.d("TEST", "apiResult" + recipes.size)
        val translateList = mutableListOf<RecipeEntity>()
        recipes.forEach {
            translateList.add(controller.toRecipeEntity(it))
            Log.d("TEST", it.title)
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
