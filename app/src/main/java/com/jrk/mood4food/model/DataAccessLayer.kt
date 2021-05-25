package com.jrk.mood4food.model

import com.jrk.mood4food.recipes.detail.model.RecipeEntity
import com.jrk.mood4food.recipes.detail.model.RecipeRepository
import com.jrk.mood4food.waterbalance.model.*
import kotlin.reflect.KFunction1

class DataAccessLayer(
        private val waterRepository: WaterRepository,
        private val recipeRepository: RecipeRepository,
        private val settingsRepository: SettingsRepository
) {
    private val observers = mutableListOf<DomainObservers>()

    fun getWaterRepository(): WaterRepository {
        return waterRepository
    }

    fun getRecipeRepository(): RecipeRepository {
        return recipeRepository
    }

    fun getSettingsRepository(): SettingsRepository {
        return settingsRepository
    }

    fun register(observer: DomainObservers) = observers.add(observer)
    fun unregister(observer: DomainObservers) = observers.remove(observer)


    fun performWaterAdd(waterAdd: Float) {
        getWaterRepository().storeWaterBalance(waterAdd)
        notifyW(WaterBalanceObserver::waterStoredIn as KFunction1<DomainObservers, Unit>)

    }

    fun performWaterReset() {
        getWaterRepository().resetWaterbalance()
        notifyW(WaterBalanceObserver::waterStoredIn as KFunction1<DomainObservers, Unit>)
    }

    fun saveRecipe(recipe: RecipeEntity) {
        getRecipeRepository().storeRecipe(recipe)
        //TODO how to give recipe as parameter to "recipeSaved()"?
        //notify(Add_ModObserver::recipeSaved as KFunction1<DomainObservers, Unit>)
    }


    private fun notifyS(action: KFunction1<SettingsObserver, Unit>) {
        observers.filterIsInstance<SettingsObserver>().onEach { action(it) }
    }

    private fun notifyW(action: KFunction1<WaterBalanceObserver, Unit>) {
        observers.filterIsInstance<WaterBalanceObserver>().onEach { action(it) }
    }

    private fun notifyR(action: KFunction1<WaterBalanceObserver, Unit>) {
        //für Rezepte siehe notifyW und notifyS
    }


    fun performCalculateNeeds(calculationData: SettingsEntity) {
        getSettingsRepository().calculateNeeds(calculationData)
        notifyS(SettingsObserver::calculationOfNeedsDone as KFunction1<DomainObservers, Unit>)
    }


    fun saveChangedGoals(data: SettingsEntity) {
        getSettingsRepository().calculateChangedGoals(data)
        notifyW(WaterBalanceObserver::goalsChanged)
    }

    fun firstStartApp() {
        waterRepository.firstStart()
        settingsRepository.firstStart()
    }


}
