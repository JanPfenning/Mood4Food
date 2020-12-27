package com.jrk.mood4food.settings.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import com.jrk.mood4food.NavBarActivity
import com.jrk.mood4food.R
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.settings.controller.SetIngredientsController
import com.jrk.mood4food.waterbalance.model.SettingsObserver
import com.jrk.mood4food.waterbalance.view.SettingsView

class SetIngredientsActivity : NavBarActivity(), SettingsView, SettingsObserver {
    private val model = ModelModule.dataAccessLayer
    private val controller = SetIngredientsController(model)

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_favorite_ingredients)
        val ingredientsGoodList = findViewById<ListView>(R.id.mod_ingredient_good_list)
        val ingredientsBadList = findViewById<ListView>(R.id.mod_ingredient_bad_list)
        var ingredientsGood : MutableSet<IngredientSettings> = mutableSetOf()
        var ingredientsBad : MutableSet<IngredientSettings> = mutableSetOf()
        controller.bind(this)
        findViewById<ImageView>(R.id.backToSettings).setOnClickListener{
            finish()
        }
        findViewById<ImageView>(R.id.addIngredientGood).setOnClickListener{
            ingredientsGood.add(IngredientSettings())
            ingredientsGoodList.adapter = IngredientAdapterSettings(
                    ingredientsGood.toTypedArray(),
                    this
            )
            (ingredientsGoodList.adapter as IngredientAdapterSettings).notifyDataSetChanged()
        }
        findViewById<ImageView>(R.id.addIngredientBad).setOnClickListener{
            ingredientsBad.add(IngredientSettings())
            ingredientsBadList.adapter = IngredientAdapterSettings(
                    ingredientsBad.toTypedArray(),
                    this
            )
            (ingredientsBadList.adapter as IngredientAdapterSettings).notifyDataSetChanged()
        }

        super.onCreate(savedInstanceState)


    }







    override fun calculationOfNeedsDone() {}

}