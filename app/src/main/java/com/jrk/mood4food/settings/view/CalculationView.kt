package com.jrk.mood4food.settings.view

import com.jrk.mood4food.waterbalance.model.SettingsEntity

interface CalculationView {
    fun getCalculationData(): SettingsEntity
    fun showCurrentSettings()
}