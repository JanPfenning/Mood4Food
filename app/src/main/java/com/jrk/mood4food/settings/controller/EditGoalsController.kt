package com.jrk.mood4food.settings.controller

import com.jrk.mood4food.model.DataAccessLayer
import com.jrk.mood4food.settings.view.EditGoalsActivity
import com.jrk.mood4food.waterbalance.model.SettingsEntity

class EditGoalsController(private val model: DataAccessLayer) {
    private  lateinit var view: EditGoalsActivity
    fun bind(settingsView: EditGoalsActivity) {
        view = settingsView
    }

    fun saveChangedGoals(data: SettingsEntity) {
        model.saveChangedGoals(data)
    }


}