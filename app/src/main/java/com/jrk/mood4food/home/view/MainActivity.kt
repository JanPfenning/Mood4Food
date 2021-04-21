package com.jrk.mood4food.home.view

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import com.jrk.mood4food.NavBarActivity
import com.jrk.mood4food.R
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.model.api.RecipeEndpoint
import com.jrk.mood4food.model.api.entity.Recipe
import kotlin.reflect.KFunction1

class MainActivity : NavBarActivity() {

    private val model = ModelModule.dataAccessLayer

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_home)
        super.onCreate(savedInstanceState)

        val waterBalance = model.getWaterRepository().getCurrentWaterBalance()

        val waterProgressBar = findViewById<ProgressBar>(R.id.homeWaterProgressBar)
        waterProgressBar.setProgress(waterBalance.toInt(), false)


        RecipeEndpoint.getAll(applicationContext, ::printAll as KFunction1<List<Any>, Unit>)

    }

    fun printAll(recipes: List<Recipe>){
        recipes.forEach { Log.e("APICON", it.title) }
    }

}