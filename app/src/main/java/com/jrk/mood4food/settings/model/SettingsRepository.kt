package com.jrk.mood4food.waterbalance.model

import android.content.Context
import android.util.Log
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
        var entities = LocalStorage.getAll(App.getContext(), SettingsEntity::class.java) as List<SettingsEntity>

        lateinit var  entitie:SettingsEntity
        if (entities.isEmpty()){
            var entitie = SettingsEntity(App.getContext())
        }else{
            entitie = entities[0]
        }


        entitie.currentBodyWeight = this.calculationData.currentBodyWeight
        entitie.age = this.calculationData.age
        entitie.aimBodyWeight = this.calculationData.aimBodyWeight
        entitie.bodySize = this.calculationData.bodySize
        entitie.caloriesPerDay = this.calculationData.caloriesPerDay
        entitie.carbohydratesPerDay = this.calculationData.carbohydratesPerDay
        entitie.proteinPerDay = this.calculationData.proteinPerDay
        entitie.fatPerDay = this.calculationData.fatPerDay
        entitie.gender = this.calculationData.gender.name
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
            caloriesPerDay =  ((13.7 * calculationData.currentBodyWeight) + (5 * calculationData.bodySize) - (6.8 * calculationData.age)).toInt()
        }
        if (calculationData.gender == Gender.Female)
            caloriesPerDay =  ((655.1 + (9.6 * calculationData.currentBodyWeight) + (1.8 * calculationData.currentBodyWeight) - (4.7 * calculationData.age))).toInt()
        return  caloriesPerDay
    }

    private fun calcWaterPerDay(): Float {
        return Math.round((((calculationData.currentBodyWeight * calculationData.age)/ 28.3F) * 0.03F)*10.0)/10.0F
    }

    fun getCalculatedNeeds(): SettingsPhysicalConditionData {
        return calculationData

    }
    fun getSettings(): SettingsPhysicalConditionData {
        var entities = LocalStorage.getAll(App.getContext(), SettingsEntity::class.java) as List<SettingsEntity>
        var entity: SettingsEntity = entities[0]



        var currentSettings: SettingsPhysicalConditionData = SettingsPhysicalConditionData();
        currentSettings.bodySize = entity.bodySize
        currentSettings.currentBodyWeight = entity.currentBodyWeight
        currentSettings.gender = Gender.valueOf(entity.gender)
        currentSettings.age = entity.age
        return currentSettings





    }


}