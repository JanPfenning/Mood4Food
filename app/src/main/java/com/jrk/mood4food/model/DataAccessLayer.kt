package com.jrk.mood4food.model

import com.jrk.mood4food.waterbalance.model.WaterBalanceObserver
import com.jrk.mood4food.waterbalance.model.WaterRepository

class DataAccessLayer(
        private val waterRepository : WaterRepository
) {
    private val observers = mutableListOf<DomainObservers>()

    fun getWaterRepository(): WaterRepository {return waterRepository}
    fun register(observer: DomainObservers) = observers.add(observer)
    fun unregister(observer: DomainObservers) = observers.remove(observer)
    fun performWaterAdd(waterAdd: Float) {
        getWaterRepository().storeWaterBalance(waterAdd)
        notify(WaterBalanceObserver::waterStoredIn)
    }
    private fun notify(action: (WaterBalanceObserver) -> Unit) {

        observers.filterIsInstance<WaterBalanceObserver>().onEach { action(it) }
    }

}