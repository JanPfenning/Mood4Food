package com.jrk.mood4food.waterbalance.model

import com.jrk.mood4food.App
import com.jrk.mood4food.model.localStorage.LocalStorage
import com.jrk.mood4food.settings.Gender
import com.jrk.mood4food.settings.SettingsPhysicalConditionData
import com.jrk.mood4food.settings.view.IngredientSettings

class SettingsRepository {
    var currentSettings: SettingsPhysicalConditionData = SettingsPhysicalConditionData();
    fun calculateNeeds(calculationData: SettingsPhysicalConditionData) {
        this.currentSettings = calculationData
        storeSettings()
        this.currentSettings.waterPerDay = calcWaterPerDay()
        this.currentSettings.caloriesPerDay = calcCaloriesPerDay()
        this.currentSettings.proteinPerDay = calcProteinPerDay()
        this.currentSettings.carbohydratesPerDay = calcCarbohydratePerDay()
        this.currentSettings.fatPerDay = calcFatPerDay()


    }

    private fun storeSettings() {
        var entities = LocalStorage.getAll(App.getContext(), SettingsEntity::class.java) as List<SettingsEntity>

        lateinit var  entitie:SettingsEntity
        if (entities.isEmpty()){
            var entitie = SettingsEntity(App.getContext())
        }else{
            entitie = entities[0]
        }

        entitie.currentBodyWeight = if(this.currentSettings.currentBodyWeight != 0F) this.currentSettings.currentBodyWeight else entitie.currentBodyWeight
        entitie.age = if(this.currentSettings.age != 0) this.currentSettings.age else entitie.age
        entitie.aimBodyWeight = if(this.currentSettings.aimBodyWeight != 0F)  this.currentSettings.aimBodyWeight else  entitie.aimBodyWeight
        entitie.bodySize = if(currentSettings.bodySize != 0) this.currentSettings.bodySize else entitie.bodySize
        entitie.caloriesPerDay =  if(currentSettings.caloriesPerDay != 0)  this.currentSettings.caloriesPerDay else entitie.caloriesPerDay
        entitie.carbohydratesPerDay = if(currentSettings.caloriesPerDay != 0) calcCarbohydratePerDay() else entitie.carbohydratesPerDay
        entitie.proteinPerDay = if(currentSettings.caloriesPerDay != 0) calcProteinPerDay()  else entitie.proteinPerDay
        entitie.fatPerDay = if(currentSettings.caloriesPerDay != 0) calcFatPerDay() else entitie.fatPerDay
        entitie.gender =  if(currentSettings.gender != Gender.Fail )this.currentSettings.gender.name else entitie.gender
        entitie.waterPerDay = if(currentSettings.waterPerDay != 0F) this.currentSettings.waterPerDay else entitie.waterPerDay
        //entitie.saveToLocalStorage(entitie) //TODO
    }



    private fun calcCarbohydratePerDay(): Int {
        var proportion = this.currentSettings.caloriesPerDay*0.55
        return (proportion * (1/ 4.1)).toInt()

    }
    private fun calcFatPerDay(): Int {
        var proportion = this.currentSettings.caloriesPerDay*0.30
        return (proportion * (1/ 9.3)).toInt()

    }

    private fun calcProteinPerDay(): Int {
        var proportion = this.currentSettings.caloriesPerDay*0.15
        return (proportion * (1/ 4.1)).toInt()
    }


    private fun calcCaloriesPerDay(): Int {
        var caloriesPerDay = 0
        if (currentSettings.gender == Gender.Male) {
            caloriesPerDay =  ((13.7 * currentSettings.currentBodyWeight) + (5 * currentSettings.bodySize) - (6.8 * currentSettings.age)).toInt()
        }
        if (currentSettings.gender == Gender.Female)
            caloriesPerDay =  ((655.1 + (9.6 * currentSettings.currentBodyWeight) + (1.8 * currentSettings.currentBodyWeight) - (4.7 * currentSettings.age))).toInt()
        return  caloriesPerDay
    }

    private fun calcWaterPerDay(): Float {
        return Math.round((((currentSettings.currentBodyWeight * currentSettings.age)/ 28.3F) * 0.03F)*10.0)/10.0F
    }

    fun getCalculatedNeeds(): SettingsPhysicalConditionData {
        return currentSettings

    }
    fun getSettings(): SettingsPhysicalConditionData {

        var entities = LocalStorage.getAll(App.getContext(), SettingsEntity::class.java) as List<SettingsEntity>

        var entity: SettingsEntity = entities[0]
        if(entity.gender.isNullOrEmpty()){
            entity = SettingsEntity(App.getContext())
        }


        currentSettings.bodySize = entity.bodySize
        currentSettings.currentBodyWeight = entity.currentBodyWeight
        currentSettings.gender = Gender.valueOf(entity.gender)
        currentSettings.age = entity.age
        currentSettings.aimBodyWeight = entity.aimBodyWeight
        currentSettings.weightChange =  entity.weightChange
        currentSettings.weightChangePerMonth = entity.weightChangePerMonth
       // currentSettings.physicalActivity = entity.physicalActivity
        currentSettings.waterPerDay = entity.waterPerDay
        currentSettings.caloriesPerDay = entity.caloriesPerDay
        currentSettings.carbohydratesPerDay = entity.carbohydratesPerDay
        currentSettings.proteinPerDay = entity.proteinPerDay
        currentSettings.fatPerDay = entity.fatPerDay
        return currentSettings

    }

    fun saveCalculationResults() {
        storeSettings()
    }

    fun saveChangedGoals(data: SettingsPhysicalConditionData) {
        currentSettings = data
        storeSettings()
    }

    fun saveIngredients(ingredientsGood: MutableSet<IngredientSettings>, ingredientsBad: MutableSet<IngredientSettings>) {
        var ingredientsGood = ingToMutableSet(ingredientsGood)
        var ingredientsBad = ingToMutableSet(ingredientsBad)
        var entities = LocalStorage.getAll(App.getContext(), IngredientsSettingsEntity::class.java) as List<IngredientsSettingsEntity>

        var entity: IngredientsSettingsEntity = entities[0]
        if(entity.ingredientsGood.isNullOrEmpty()){
            entity = IngredientsSettingsEntity(App.getContext())
        }
        entity.ingredientsBad = ingredientsBad
        entity.ingredientsGood = ingredientsGood
        //entity.saveToLocalStorage(entity)


    }
    fun getGoodIngredients(): MutableSet<IngredientSettings> {
        var entities = LocalStorage.getAll(App.getContext(), IngredientsSettingsEntity::class.java) as List<IngredientsSettingsEntity>

        var entity: IngredientsSettingsEntity = entities[0]
        if(entity.ingredientsGood.isNullOrEmpty()){
            entity = IngredientsSettingsEntity(App.getContext())
        }
        var ingredientsGood = setToIngredient(entity.ingredientsGood)

        return ingredientsGood
    }
    fun getBadIngredients(): MutableSet<IngredientSettings> {
        var entities = LocalStorage.getAll(App.getContext(), IngredientsSettingsEntity::class.java) as List<IngredientsSettingsEntity>

        var entity: IngredientsSettingsEntity = entities[0]
        if(entity.ingredientsGood.isNullOrEmpty()){
            entity = IngredientsSettingsEntity(App.getContext())
        }
        var ingredientsBad = setToIngredient(entity.ingredientsBad)
        return ingredientsBad
    }
    private fun ingToMutableSet(set: Set<IngredientSettings>): MutableSet<Set<String>> {
        val retSet = mutableSetOf<Set<String>>()
        set.forEach{e ->
            if(!e.name.equals("")) {
                retSet.add(setOf(e.name))
            }
        }
        return retSet
    }
    private fun setToIngredient(set: Set<Set<String>>): MutableSet<IngredientSettings> {
        val retSet = mutableSetOf<IngredientSettings>()
        set.forEach{e ->
            var i = IngredientSettings()
            i.name = e.elementAt(0)
            retSet.add(i)
        }
        return retSet
    }


}