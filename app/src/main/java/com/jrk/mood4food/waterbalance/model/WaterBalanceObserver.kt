package com.jrk.mood4food.waterbalance.model

import com.jrk.mood4food.model.DomainObservers

interface WaterBalanceObserver: DomainObservers {
    fun waterStoredIn()
    fun goalsChanged()

}