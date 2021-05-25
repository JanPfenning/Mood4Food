package com.jrk.mood4food.waterbalance.model

import android.content.Context
import com.jrk.mood4food.model.localStorage.LocalEntity

class SettingsEntity(context: Context) : LocalEntity(context, SettingsEntity::class.java, false) {
    var age = 0
    var gender = "Male"
    var currentBodyWeight: Float = 0F;
    var bodySize = 0
    var aimBodyWeight: Float = 0F;
    var weightChange: Float = 0F;
    var weightChangePerMonth: Float = 0F;
    var physicalActivity: String = "Normal";
    var waterPerDay = 0F
    var caloriesPerDay = 0
    var carbohydratesPerDay = 0
    var proteinPerDay = 0
    var fatPerDay = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SettingsEntity

        if (age != other.age) return false
        if (gender != other.gender) return false
        if (currentBodyWeight != other.currentBodyWeight) return false
        if (bodySize != other.bodySize) return false
        if (aimBodyWeight != other.aimBodyWeight) return false
        if (weightChange != other.weightChange) return false
        if (weightChangePerMonth != other.weightChangePerMonth) return false
        if (physicalActivity != other.physicalActivity) return false
        if (waterPerDay != other.waterPerDay) return false
        if (caloriesPerDay != other.caloriesPerDay) return false
        if (carbohydratesPerDay != other.carbohydratesPerDay) return false
        if (proteinPerDay != other.proteinPerDay) return false
        if (fatPerDay != other.fatPerDay) return false

        return true
    }

    override fun hashCode(): Int {
        var result = age
        result = 31 * result + gender.hashCode()
        result = 31 * result + currentBodyWeight.hashCode()
        result = 31 * result + bodySize
        result = 31 * result + aimBodyWeight.hashCode()
        result = 31 * result + weightChange.hashCode()
        result = 31 * result + weightChangePerMonth.hashCode()
        result = 31 * result + physicalActivity.hashCode()
        result = 31 * result + waterPerDay.hashCode()
        result = 31 * result + caloriesPerDay
        result = 31 * result + carbohydratesPerDay
        result = 31 * result + proteinPerDay
        result = 31 * result + fatPerDay
        return result
    }


}