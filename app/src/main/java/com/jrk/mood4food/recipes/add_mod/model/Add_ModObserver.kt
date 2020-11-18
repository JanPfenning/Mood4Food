package com.jrk.mood4food.recipes.add_mod.model

import com.jrk.mood4food.model.DomainObservers

interface Add_ModObserver: DomainObservers {
    fun recipeSaved()
}