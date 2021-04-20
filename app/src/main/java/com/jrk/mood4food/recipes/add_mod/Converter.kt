package com.jrk.mood4food.recipes.add_mod

object Converter {
    fun setToIngredient(set: Set<Set<String>>): MutableSet<Ingredient> {
        val retSet = mutableSetOf<Ingredient>()
        set.forEach { e ->
            var i = Ingredient()
            i.name = e.elementAt(0)
            i.amount = e.elementAt(1)
            retSet.add(i)
        }
        return retSet
    }

    fun setToMaterials(set: Set<String>): MutableSet<Material> {
        val retSet = mutableSetOf<Material>()
        set.forEach { e ->
            var i = Material()
            i.name = e
            retSet.add(i)
        }
        return retSet
    }

    fun ingToMutableSet(set: Set<Ingredient>): MutableSet<Set<String>> {
        val retSet = mutableSetOf<Set<String>>()
        set.forEach { e ->
            if (!e.name.equals("") && !e.amount.equals("")) {
                retSet.add(setOf(e.name, e.amount))
            }
        }
        return retSet
    }

    fun matToMutableSet(set: Set<Material>): MutableSet<String> {
        val retSet = mutableSetOf<String>()
        set.forEach { e ->
            if (!e.name.equals("")) {
                retSet.add(e.name)
            }
        }
        return retSet
    }
}