package com.jrk.mood4food.waterbalance.controller

import com.jrk.mood4food.model.DataAccessLayer
import com.jrk.mood4food.settings.view.SettingsActivity
import com.jrk.mood4food.waterbalance.view.SettingsView

class SettingsControler(private val model: DataAccessLayer) {
    private  lateinit var view: SettingsView
    fun bind(settingsView: SettingsActivity) {
        view = settingsView
    }




}