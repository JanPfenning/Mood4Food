package com.jrk.mood4food.settings.controller

import com.jrk.mood4food.model.DataAccessLayer
import com.jrk.mood4food.settings.view.CalculationNeedsActivty
import com.jrk.mood4food.settings.view.EditGoalsActivity
import com.jrk.mood4food.settings.view.SettingsPhysicalConditionData

class EditGoalsController(private val model: DataAccessLayer) {
    private  lateinit var view: EditGoalsActivity
    fun bind(settingsView: EditGoalsActivity) {
        view = settingsView
    }

    fun saveChangedGoals(data: SettingsPhysicalConditionData) {
        model.saveChangedGoals(data)
    }


}