package com.jrk.mood4food.settings.controller

import com.jrk.mood4food.model.DataAccessLayer
import com.jrk.mood4food.settings.view.ResultsCalculationActivty
import com.jrk.mood4food.waterbalance.model.SettingsEntity

class ResultController (private val model: DataAccessLayer) {
    private lateinit var view: ResultsCalculationActivty
    fun bind(settingsView: ResultsCalculationActivty) {
        view = settingsView
    }

    fun saveCalculationResults(data: SettingsEntity) {
        model.getSettingsRepository().storeSettings(data)
    }

}