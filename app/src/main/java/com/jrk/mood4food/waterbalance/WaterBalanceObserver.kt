package com.jrk.mood4food.waterbalance

import com.jrk.mood4food.waterbalance.DomainObservers

interface WaterBalanceObserver: DomainObservers {
    fun waterStoredIn()

}