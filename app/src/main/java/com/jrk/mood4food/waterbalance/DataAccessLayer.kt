package com.jrk.mood4food.waterbalance

class DataAccessLayer {
    private val observers = mutableListOf<DomainObservers>()
    fun register(observer: DomainObservers) = observers.add(observer)
    fun unregister(observer: DomainObservers) = observers.remove(observer)
    fun performWaterAdd(waterAdd: Float) {

        notify(WaterBalanceObserver::waterStoredIn)
    }
    private fun notify(action: (WaterBalanceObserver) -> Unit) {

        observers.filterIsInstance<WaterBalanceObserver>().onEach { action(it) }
    }
}