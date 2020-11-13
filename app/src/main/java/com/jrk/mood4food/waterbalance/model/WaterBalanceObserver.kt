package com.jrk.mood4food.waterbalance.model

import com.jrk.mood4food.waterbalance.model.DomainObservers

interface WaterBalanceObserver: DomainObservers {
    fun waterStoredIn()

}