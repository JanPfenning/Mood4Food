package com.jrk.mood4food.waterbalance.model.Observer

import com.jrk.mood4food.waterbalance.model.Observer.DomainObservers

interface WaterBalanceObserver: DomainObservers {
    fun waterStoredIn()

}