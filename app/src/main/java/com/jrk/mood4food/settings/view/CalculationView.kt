package com.jrk.mood4food.settings.view

import com.jrk.mood4food.settings.SettingsPhysicalConditionData

interface CalculationView {
    fun getCalculationData(): SettingsPhysicalConditionData
    fun showCurrentSettings()
}