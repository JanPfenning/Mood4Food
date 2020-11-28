package com.jrk.mood4food.waterbalance.model

import android.content.Context
import com.jrk.mood4food.App
import com.jrk.mood4food.model.localStorage.LocalStorage
import com.jrk.mood4food.settings.Gender
import com.jrk.mood4food.settings.view.SettingsPhysicalConditionData

class SettingsRepository {
    lateinit var calculationData: SettingsPhysicalConditionData
    fun calculateNeeds(calculationData: SettingsPhysicalConditionData) {
        this.calculationData = calculationData
        this.calculationData.waterPerDay = calcWaterPerDay()
        this.calculationData.caloriesPerDay = calcCaloriesPerDay()
        this.calculationData.proteinPerDay = calcProteinPerDay()
        this.calculationData.carbohydratesPerDay = calcCarbohydratePerDay()
        this.calculationData.fatPerDay = calcFatPerDay()
        storeSettings()

    }

    private fun storeSettings() {
        val context: Context = App.getContext()
        var entities = LocalStorage.getAll(App.getContext(), SettingsEntity::class.java) as List<SettingsEntity>
        lateinit var  entitie:SettingsEntity
        if (entities.isEmpty()){
            var entitie = SettingsEntity(context)
        }else{
            entitie = entities[0]
        }


        entitie.actualBodyWeight = this.calculationData.actualBodyWeight
        entitie.age = this.calculationData.age
        entitie.aimBodyWeight = this.calculationData.aimBodyWeight
        entitie.bodyHeight = this.calculationData.bodyHeight
        entitie.caloriesPerDay = this.calculationData.caloriesPerDay
        entitie.carbohydratesPerDay = this.calculationData.carbohydratesPerDay
        entitie.proteinPerDay = this.calculationData.proteinPerDay
        entitie.fatPerDay = this.calculationData.fatPerDay
        entitie.gender = this.calculationData.gender
        entitie.waterPerDay = this.calculationData.waterPerDay

        entitie.saveToLocalStorage(entitie)
    }



    private fun calcCarbohydratePerDay(): Int {
        var proportion = this.calculationData.caloriesPerDay*0.55
        return (proportion * (1/ 4.1)).toInt()

    }
    private fun calcFatPerDay(): Int {
        var proportion = this.calculationData.caloriesPerDay*0.30
        return (proportion * (1/ 9.3)).toInt()

    }

    private fun calcProteinPerDay(): Int {
        var proportion = this.calculationData.caloriesPerDay*0.15
        return (proportion * (1/ 4.1)).toInt()
    }


    private fun calcCaloriesPerDay(): Int {
        var caloriesPerDay = 0
        if (calculationData.gender == Gender.Male) {
            caloriesPerDay =  ((13.7 * calculationData.actualBodyWeight) + (5 * calculationData.bodyHeight) - (6.8 * calculationData.age)).toInt()
        }
        if (calculationData.gender == Gender.Female)
            caloriesPerDay =  ((655.1 + (9.6 * calculationData.actualBodyWeight) + (1.8 * calculationData.bodyHeight) - (4.7 * calculationData.age))).toInt()
        return  caloriesPerDay
    }

    private fun calcWaterPerDay(): Float {
        return Math.round((((calculationData.actualBodyWeight * calculationData.age)/ 28.3F) * 0.03F)*10.0)/10.0F
    }

    fun getCalculatedNeeds(): SettingsPhysicalConditionData {
        return calculationData

    }


}