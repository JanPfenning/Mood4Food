package com.jrk.mood4food.waterbalance.model

import android.content.Context
import com.jrk.mood4food.model.localStorage.LocalEntity


class IngredientsSettingsEntity(context: Context) : LocalEntity(context, IngredientsSettingsEntity::class.java, false) {
    var ingredientsGood:Set<Set<String>> = emptySet()
    var ingredientsBad:Set<Set<String>> = emptySet()







}