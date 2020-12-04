package com.jrk.mood4food.settings.controller

import com.jrk.mood4food.model.DataAccessLayer
import com.jrk.mood4food.settings.view.CalculationNeedsActivty
import com.jrk.mood4food.settings.view.ResultsCalculationActivty

class ResultController (private val model: DataAccessLayer) {
    private  lateinit var view: ResultsCalculationActivty
    fun bind(settingsView: ResultsCalculationActivty) {
        view = settingsView
    }

}