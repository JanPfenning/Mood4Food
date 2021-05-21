package com.jrk.mood4food.waterbalance.model

import android.util.Log
import com.jrk.mood4food.App
import com.jrk.mood4food.model.localStorage.LocalStorageInterface
import com.jrk.mood4food.settings.PhysicalActivity
import com.jrk.mood4food.settings.view.IngredientSettings
import kotlin.math.roundToInt

class SettingsRepository(localStorage: LocalStorageInterface) {
    var localStorage = localStorage
    lateinit var tmpEnitity: SettingsEntity

    fun calculateNeeds(calculationData: SettingsEntity) {
        calculationData.waterPerDay = calcWaterPerDay(calculationData)
        calculationData.caloriesPerDay = calcCaloriesPerDay(calculationData)
        calculationData.proteinPerDay = calcProteinPerDay(calculationData)
        calculationData.carbohydratesPerDay = calcCarbohydratePerDay(calculationData)
        calculationData.fatPerDay = calcFatPerDay(calculationData)
        tmpEnitity = calculationData

    }

    fun storeSettings(calculationData: SettingsEntity) {
        localStorage.save(App.getContext(), calculationData)
    }


    private fun calcCarbohydratePerDay(calculationData: SettingsEntity): Int {
        var proportion = calculationData.caloriesPerDay * 0.55
        return (proportion * (1 / 4.1)).toInt()

    }

    private fun calcFatPerDay(calculationData: SettingsEntity): Int {
        var proportion = calculationData.caloriesPerDay * 0.30
        return (proportion * (1 / 9.3)).toInt()

    }

    private fun calcProteinPerDay(calculationData: SettingsEntity): Int {
        var proportion = calculationData.caloriesPerDay * 0.15
        return (proportion * (1 / 4.1)).toInt()
    }


    private fun calcCaloriesPerDay(calculationData: SettingsEntity): Int {
        var caloriesPerDay = 0
        if (calculationData.gender == "Male") {
            caloriesPerDay = ((13.7 * calculationData.currentBodyWeight) + (5 * calculationData.bodySize) - (6.8 * calculationData.age)).toInt()
        }
        if (calculationData.gender == "Female")
            caloriesPerDay = ((655.1 + (9.6 * calculationData.currentBodyWeight) + (1.8 * calculationData.currentBodyWeight) - (4.7 * calculationData.age))).toInt()
        Log.i("test", calculationData.physicalActivity)

        var faktor: Float
        when (PhysicalActivity.getByText(calculationData.physicalActivity)) {
            PhysicalActivity.Lowest -> faktor = 1.2F
            PhysicalActivity.Low -> faktor = 1.375F
            PhysicalActivity.Middle -> faktor = 1.55F
            PhysicalActivity.High -> faktor = 1.725F
            PhysicalActivity.Highest -> faktor = 1.9F
            else -> {
                faktor = 1F
            }
        }

        return (caloriesPerDay * faktor).toInt()
    }

    private fun calcWaterPerDay(calculationData: SettingsEntity): Float {
        return ((((calculationData.currentBodyWeight * calculationData.age) / 28.3F) * 0.03F) * 10.0).roundToInt() / 10.0F
    }

    fun getSettings(): SettingsEntity {

        var entities = localStorage.getAll(App.getContext(), SettingsEntity::class.java) as List<SettingsEntity>

        var entity: SettingsEntity = entities[0]
        if (entity.gender.isNullOrEmpty()) {
            entity = SettingsEntity(App.getContext())
        }
        return entity

    }


    fun saveIngredients(ingredientsGood: MutableSet<IngredientSettings>, ingredientsBad: MutableSet<IngredientSettings>) {
        var ingredientsGood = ingToMutableSet(ingredientsGood)
        var ingredientsBad = ingToMutableSet(ingredientsBad)
        var entities = localStorage.getAll(App.getContext(), IngredientsSettingsEntity::class.java) as List<IngredientsSettingsEntity>

        var entity: IngredientsSettingsEntity = entities[0]
        if (entity.ingredientsGood.isNullOrEmpty()) {
            entity = IngredientsSettingsEntity(App.getContext())
        }
        entity.ingredientsBad = ingredientsBad
        entity.ingredientsGood = ingredientsGood
        localStorage.save(App.getContext(), entity)


    }

    fun getGoodIngredients(): MutableSet<IngredientSettings> {
        var entities = localStorage.getAll(App.getContext(), IngredientsSettingsEntity::class.java) as List<IngredientsSettingsEntity>

        var entity: IngredientsSettingsEntity = entities[0]
        if (entity.ingredientsGood.isNullOrEmpty()) {
            entity = IngredientsSettingsEntity(App.getContext())
        }

        return setToIngredient(entity.ingredientsGood)
    }

    fun getBadIngredients(): MutableSet<IngredientSettings> {
        var entities = localStorage.getAll(App.getContext(), IngredientsSettingsEntity::class.java) as List<IngredientsSettingsEntity>

        var entity: IngredientsSettingsEntity = entities[0]
        if (entity.ingredientsGood.isNullOrEmpty()) {
            entity = IngredientsSettingsEntity(App.getContext())
        }
        return setToIngredient(entity.ingredientsBad)
    }

    private fun ingToMutableSet(set: Set<IngredientSettings>): MutableSet<Set<String>> {
        val retSet = mutableSetOf<Set<String>>()
        set.forEach { e ->
            if (e.name != "") {
                retSet.add(setOf(e.name))
            }
        }
        return retSet
    }

    private fun setToIngredient(set: Set<Set<String>>): MutableSet<IngredientSettings> {
        val retSet = mutableSetOf<IngredientSettings>()
        set.forEach { e ->
            var i = IngredientSettings()
            i.name = e.elementAt(0)
            retSet.add(i)
        }
        return retSet
    }

    fun getTempEntity(): SettingsEntity {
        return tmpEnitity

    }


}