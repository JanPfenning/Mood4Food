package com.jrk.mood4food.model

import com.jrk.mood4food.recipe.model.RecipeObserver
import com.jrk.mood4food.recipe.model.RecipeRepository
import com.jrk.mood4food.waterbalance.model.WaterBalanceObserver
import com.jrk.mood4food.waterbalance.model.WaterRepository

class DataAccessLayer(
        private val waterRepository : WaterRepository,
        private val recipeRepository: RecipeRepository
) {
    private val observers = mutableListOf<DomainObservers>()

    fun getWaterRepository(): WaterRepository {return waterRepository}
    fun getRecipeRepository(): RecipeRepository{return recipeRepository}

    fun register(observer: DomainObservers) = observers.add(observer)
    fun unregister(observer: DomainObservers) = observers.remove(observer)

    fun performWaterAdd(waterAdd: Float) {
        getWaterRepository().storeWaterBalance(waterAdd)
        notify(WaterBalanceObserver::waterStoredIn)
    }

    fun performRecipeAdd(title:String,ingredients:List<String>,materials:List<String>,description:String,steps:List<String>){
        getRecipeRepository().storeNewRecipe(title,ingredients,materials,description,steps)
        notifyRecipe(RecipeObserver::recipeStoredIn)
    }

    //TODO
    private fun notify(action: (WaterBalanceObserver) -> Unit) {
        observers.filterIsInstance<WaterBalanceObserver>().onEach { action(it) }
    }
    private fun notifyRecipe(action: (RecipeObserver) -> Unit) {
        observers.filterIsInstance<RecipeObserver>().onEach { action(it) }
    }

}