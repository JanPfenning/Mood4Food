package com.jrk.mood4food.settings.model

import com.jrk.mood4food.settings.view.IngredientSettings

object SettingsConverter {
    public fun ingToMutableSet(set: Set<IngredientSettings>): MutableSet<Set<String>> {
        val retSet = mutableSetOf<Set<String>>()
        set.forEach { e ->
            if (e.name != "") {
                retSet.add(setOf(e.name))
            }
        }
        return retSet
    }

    public fun setToIngredient(set: Set<Set<String>>): MutableSet<IngredientSettings> {
        val retSet = mutableSetOf<IngredientSettings>()
        set.forEach { e ->
            var i = IngredientSettings()
            i.name = e.elementAt(0)
            retSet.add(i)
        }
        return retSet
    }
}