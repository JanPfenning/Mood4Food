package com.jrk.mood4food.waterbalance.model

import com.jrk.mood4food.waterbalance.model.Observer.DomainObservers
import com.jrk.mood4food.waterbalance.model.Observer.WaterBalanceObserver

class DataAccessLayer(
        private val waterRepository : WaterRepository
) {
    private val observers = mutableListOf<DomainObservers>()

    fun register(observer: DomainObservers) = observers.add(observer)
    fun unregister(observer: DomainObservers) = observers.remove(observer)
    fun performWaterAdd(waterAdd: Float) {
        setCurrentWaterBalance(waterAdd)
        notify(WaterBalanceObserver::waterStoredIn)
    }
    private fun notify(action: (WaterBalanceObserver) -> Unit) {

        observers.filterIsInstance<WaterBalanceObserver>().onEach { action(it) }
    }

    fun getCurrentWaterBalance(): Float {
        return waterRepository.getCurrentWaterBalance()

    }
    fun setCurrentWaterBalance(wb:Float){
        waterRepository.storeWaterBalance(wb)
    }


}