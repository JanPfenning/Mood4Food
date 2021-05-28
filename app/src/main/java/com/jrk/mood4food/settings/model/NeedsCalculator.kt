package com.jrk.mood4food.settings.model

import com.jrk.mood4food.settings.PhysicalActivity
import com.jrk.mood4food.waterbalance.model.SettingsEntity
import kotlin.math.roundToInt

object NeedsCalculator {
    fun calcCarbohydratePerDay(calculationData: SettingsEntity): Int {
        var proportion = calculationData.caloriesPerDay * 0.55
        return (proportion * (1 / 4.1)).toInt()

    }

    fun calcFatPerDay(calculationData: SettingsEntity): Int {
        var proportion = calculationData.caloriesPerDay * 0.30
        return (proportion * (1 / 9.3)).toInt()

    }

    fun calcProteinPerDay(calculationData: SettingsEntity): Int {
        var proportion = calculationData.caloriesPerDay * 0.15
        return (proportion * (1 / 4.1)).toInt()
    }


    fun calcCaloriesPerDay(calculationData: SettingsEntity): Int {
        var caloriesPerDay = 0
        if (calculationData.gender == "Male") {
            caloriesPerDay = ((13.7 * calculationData.currentBodyWeight) + (5 * calculationData.bodySize) - (6.8 * calculationData.age)).toInt()
        }
        if (calculationData.gender == "Female")
            caloriesPerDay = ((655.1 + (9.6 * calculationData.currentBodyWeight) + (1.8 * calculationData.currentBodyWeight) - (4.7 * calculationData.age))).toInt()

        var factor: Float = when (PhysicalActivity.getByText(calculationData.physicalActivity)) {
            PhysicalActivity.Lowest -> 1.2F
            PhysicalActivity.Low -> 1.375F
            PhysicalActivity.Middle -> 1.55F
            PhysicalActivity.High -> 1.725F
            PhysicalActivity.Highest -> 1.9F
            else -> {
                1F
            }
        }

        return (caloriesPerDay * factor).toInt()
    }

    fun calcWaterPerDay(calculationData: SettingsEntity): Float {
        return ((((calculationData.currentBodyWeight * calculationData.age) / 28.3F) * 0.03F) * 10.0).roundToInt() / 10.0F
    }
}