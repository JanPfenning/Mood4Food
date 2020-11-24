package com.jrk.mood4food.model

import com.jrk.mood4food.recipes.add_mod.model.Add_ModObserver
import com.jrk.mood4food.recipes.detail.model.RecipeEntity
import com.jrk.mood4food.recipes.detail.model.RecipeRepository
import com.jrk.mood4food.waterbalance.model.WaterBalanceObserver
import com.jrk.mood4food.waterbalance.model.WaterRepository
import kotlin.reflect.KFunction1

class DataAccessLayer(
        private val waterRepository : WaterRepository,
        private val recipeRepository: RecipeRepository
) {
    private val observers = mutableListOf<DomainObservers>()

    fun getWaterRepository(): WaterRepository {return waterRepository}
    fun getRecipeRepository(): RecipeRepository {return recipeRepository}

    fun register(observer: DomainObservers) = observers.add(observer)
    fun unregister(observer: DomainObservers) = observers.remove(observer)


    fun performWaterAdd(waterAdd: Float) {
        getWaterRepository().storeWaterBalance(waterAdd)
        notify(WaterBalanceObserver::waterStoredIn as KFunction1<DomainObservers, Unit>)
    }

    fun saveRecipe(recipe: RecipeEntity) {
        getRecipeRepository().storeRecipe(recipe)
        //TODO how to give recipe as parameter to "recipeSaved()"?
        notify(Add_ModObserver::recipeSaved as KFunction1<DomainObservers, Unit>)
    }

    private fun notify(action: KFunction1<DomainObservers, Unit>) {
        observers.filterIsInstance<DomainObservers>().onEach { action(it) }
    }
}