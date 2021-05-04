package com.jrk.mood4food.recipes.add_mod
import com.jrk.mood4food.model.api.entity.Ingredient as ApiIng
import com.jrk.mood4food.model.api.entity.Material as ApiMat

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

    fun apiToIngredient(al: ArrayList<ApiIng>): MutableSet<Ingredient> {
        val retSet = mutableSetOf<Ingredient>()
        al.forEach { e ->
            var i = Ingredient()
            i.name = e.name
            i.amount = e.amount
            retSet.add(i)
        }
        return retSet
    }

    fun apiToMaterial(al: ArrayList<ApiMat>): MutableSet<String> {
        val retSet = mutableSetOf<String>()
        al.forEach { e ->
            retSet.add(e.name)
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