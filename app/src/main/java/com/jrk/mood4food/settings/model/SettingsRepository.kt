package com.jrk.mood4food.waterbalance.model

import com.jrk.mood4food.settings.Gender
import com.jrk.mood4food.settings.view.SettingsPhysicalConditionData

class SettingsRepository {
    lateinit var calculationData: SettingsPhysicalConditionData
    fun calculateNeeds(calculationData: SettingsPhysicalConditionData) {
        this.calculationData = calculationData
        this.calculationData.waterPerDay = calcWaterPerDay(calculationData)
        this.calculationData.caloriesPerDay = calcCaloriesPerDay(calculationData)
    }

    private fun calcCaloriesPerDay(calculationData: SettingsPhysicalConditionData): Int {
        var caloriesPerDay = 0
        if (calculationData.gender == Gender.Male) {
            caloriesPerDay =  ((13.7 * calculationData.actualBodyWeight) + (5 * calculationData.bodyHeight) - (6.8 * calculationData.age)).toInt()
        }
        if (calculationData.gender == Gender.Female)
            caloriesPerDay =  ((655.1 + (9.6 * calculationData.actualBodyWeight) + (1.8 * calculationData.bodyHeight) - (4.7 * calculationData.age))).toInt()
        return  caloriesPerDay
    }

    private fun calcWaterPerDay(calculationData: SettingsPhysicalConditionData): Float {
        return ((calculationData.actualBodyWeight * calculationData.age)/ 28.3F) * 0.03F
    }

    fun getCalculatedNeeds(): SettingsPhysicalConditionData {
        return calculationData

    }


}