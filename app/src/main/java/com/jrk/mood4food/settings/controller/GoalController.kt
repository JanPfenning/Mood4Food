package com.jrk.mood4food.settings.controller

import com.jrk.mood4food.model.DataAccessLayer
import com.jrk.mood4food.settings.view.GoalsActivty
import com.jrk.mood4food.settings.view.SettingsActivity
import com.jrk.mood4food.waterbalance.view.SettingsView

class GoalController(private val model: DataAccessLayer) {
    private  lateinit var view: GoalsActivty
    fun bind(settingsView: GoalsActivty) {
        view = settingsView
    }

    fun calculateNeeds() {

        model.performCalculateNeeds(view.getCalculationData())

    }
}