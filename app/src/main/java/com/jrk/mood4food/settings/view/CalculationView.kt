package com.jrk.mood4food.settings.view

interface CalculationView {
    fun getCalculationData():SettingsPhysicalConditionData
    fun showCurrentSettings()
}