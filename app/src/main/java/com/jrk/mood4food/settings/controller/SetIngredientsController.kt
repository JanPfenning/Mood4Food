package com.jrk.mood4food.settings.controller

import com.jrk.mood4food.model.DataAccessLayer
import com.jrk.mood4food.settings.view.IngredientSettings
import com.jrk.mood4food.settings.view.SetIngredientsActivity
import com.jrk.mood4food.settings.view.SettingsActivity
import com.jrk.mood4food.waterbalance.view.SettingsView

class SetIngredientsController (private val model: DataAccessLayer) {
    private  lateinit var view: SettingsView
    fun bind(settingsView: SetIngredientsActivity) {
        view = settingsView
    }

    fun saveIngredients(ingredientsGood: MutableSet<IngredientSettings>, ingredientsBad: MutableSet<IngredientSettings>) {
        model.getSettingsRepository().saveIngredients(ingredientsGood,ingredientsBad )
    }
}