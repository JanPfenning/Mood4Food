package com.jrk.mood4food.settings.controller

import com.jrk.mood4food.model.DataAccessLayer
import com.jrk.mood4food.settings.view.CalculationNeedsActivty

class GoalController(private val model: DataAccessLayer) {
    private  lateinit var view: CalculationNeedsActivty
    fun bind(settingsView: CalculationNeedsActivty) {
        view = settingsView
    }

    fun calculateNeeds() {

        model.performCalculateNeeds(view.getCalculationData())

    }
}