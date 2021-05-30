package com.jrk.mood4food.waterbalance.model

import com.jrk.mood4food.App
import com.jrk.mood4food.model.localStorage.LocalStorageInterface
import com.jrk.mood4food.settings.model.IngredientType
import com.jrk.mood4food.settings.model.NeedsCalculator
import com.jrk.mood4food.settings.model.SettingsConverter
import com.jrk.mood4food.settings.view.IngredientSettings

class SettingsRepository(localStorage: LocalStorageInterface) {
    var localStorage = localStorage
    private lateinit var tampEntity: SettingsEntity

    fun calculateNeeds(calculationData: SettingsEntity) {
        calculationData.waterPerDay = NeedsCalculator.calcWaterPerDay(calculationData)
        calculationData.caloriesPerDay = NeedsCalculator.calcCaloriesPerDay(calculationData)
        calculationData.proteinPerDay = NeedsCalculator.calcProteinPerDay(calculationData)
        calculationData.carbohydratesPerDay = NeedsCalculator.calcCarbohydratePerDay(calculationData)
        calculationData.fatPerDay = NeedsCalculator.calcFatPerDay(calculationData)
        tampEntity = calculationData

    }

    fun storeSettings(calculationData: SettingsEntity) {
        localStorage.save(App.getContext(), calculationData)
    }

    fun getSettings(): SettingsEntity {

        var entities = localStorage.getAll(App.getContext(), SettingsEntity::class.java) as List<SettingsEntity>


        if (entities[0] == SettingsEntity(App.getContext())) {
            return SettingsEntity(App.getContext())

        }
        return entities[0]


    }

    fun saveIngredients(ingredientsGood: MutableSet<IngredientSettings>, ingredientsBad: MutableSet<IngredientSettings>) {
        var ingredientsGood = SettingsConverter.ingToMutableSet(ingredientsGood)
        var ingredientsBad = SettingsConverter.ingToMutableSet(ingredientsBad)
        var entities = localStorage.getAll(App.getContext(), IngredientsSettingsEntity::class.java) as List<IngredientsSettingsEntity>

        var entity: IngredientsSettingsEntity = entities[0]
        if (entity.ingredientsGood.isNullOrEmpty()) {
            entity = IngredientsSettingsEntity(App.getContext())
        }
        entity.ingredientsBad = ingredientsBad
        entity.ingredientsGood = ingredientsGood
        localStorage.save(App.getContext(), entity)


    }

    fun getIngredients(ingredientType: IngredientType): MutableSet<IngredientSettings> {
        var entities = localStorage.getAll(App.getContext(), IngredientsSettingsEntity::class.java) as List<IngredientsSettingsEntity>
        var entity: IngredientsSettingsEntity = entities[0]
        return if (ingredientType == IngredientType.Bad) {
            SettingsConverter.setToIngredient(entity.ingredientsBad)
        } else {
            SettingsConverter.setToIngredient(entity.ingredientsGood)
        }


    }


    fun getTempEntity(): SettingsEntity {
        return tampEntity
    }

    fun calculateChangedGoals(changedSettings: SettingsEntity) {
        val currentSettings = getSettings()
        currentSettings.waterPerDay = NeedsCalculator.calcWaterPerDay(currentSettings)

        if (currentSettings.caloriesPerDay != changedSettings.caloriesPerDay) {

            currentSettings.caloriesPerDay = changedSettings.caloriesPerDay
            currentSettings.proteinPerDay = NeedsCalculator.calcProteinPerDay(currentSettings)
            currentSettings.carbohydratesPerDay = NeedsCalculator.calcCarbohydratePerDay(currentSettings)
            currentSettings.fatPerDay = NeedsCalculator.calcFatPerDay(currentSettings)
        } else if (currentSettings.physicalActivity != changedSettings.physicalActivity) {
            currentSettings.physicalActivity = changedSettings.physicalActivity
            currentSettings.caloriesPerDay = NeedsCalculator.calcCaloriesPerDay(currentSettings)
            currentSettings.proteinPerDay = NeedsCalculator.calcProteinPerDay(currentSettings)
            currentSettings.carbohydratesPerDay = NeedsCalculator.calcCarbohydratePerDay(currentSettings)
            currentSettings.fatPerDay = NeedsCalculator.calcFatPerDay(currentSettings)
        } else {
            currentSettings.caloriesPerDay = (changedSettings.proteinPerDay * 4.1).toInt()
            currentSettings.caloriesPerDay += (changedSettings.carbohydratesPerDay * 4.1).toInt()
            currentSettings.caloriesPerDay += (changedSettings.fatPerDay * 9.3).toInt()
            currentSettings.proteinPerDay = changedSettings.proteinPerDay
            currentSettings.carbohydratesPerDay = changedSettings.carbohydratesPerDay
            currentSettings.fatPerDay = changedSettings.fatPerDay
        }
        storeSettings(currentSettings)


    }

    fun firstStart() {
        val settingsEntity = getSettings()
        settingsEntity.waterPerDay = 2.0F
        storeSettings(settingsEntity)
    }


}