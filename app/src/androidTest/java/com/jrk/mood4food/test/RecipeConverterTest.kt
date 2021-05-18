package com.jrk.mood4food.test

import com.jrk.mood4food.recipes.add_mod.Converter
import com.jrk.mood4food.recipes.add_mod.Converter.apiToIngredient
import com.jrk.mood4food.model.api.entity.Material as ApiMat
import com.jrk.mood4food.recipes.add_mod.Ingredient
import com.jrk.mood4food.recipes.add_mod.Material
import org.junit.Before
import org.junit.Test
import com.jrk.mood4food.model.api.entity.Ingredient as ApiIng

import org.junit.Assert.*
import java.lang.reflect.Type

class RecipeConverterTest {

    @Test
    fun setToIngredientTest() {
        val ing1 = setOf("Pommes", "500g")
        val ing2 = setOf("Ketchup", "1kg")
        val inputSet: Set<Set<String>> = setOf(ing1,ing2)

        val retSet = Converter.setToIngredient(inputSet)

        val compIng1 = Ingredient();
        compIng1.name = "Pommes"
        compIng1.amount = "500g"
        val compIng2 = Ingredient();
        compIng2.name = "Ketchup"
        compIng2.amount = "1kg"
        val compSet = mutableSetOf<Ingredient>(compIng1, compIng2)

        assertEquals(compIng1.name, retSet.elementAt(0).name);
        assertEquals(compIng1.amount, retSet.elementAt(0).amount);
        assertEquals(compIng2.name, retSet.elementAt(1).name);
        assertEquals(compIng2.amount, retSet.elementAt(1).amount);
    }

    @Test
    fun setToMaterialsTest() {
        val mat = "Blech"
        val mat2 = "Pfannenwender"
        val inputSet: Set<String> = setOf(mat, mat2)

        val retSet = Converter.setToMaterials(inputSet)

        val compMat = Material();
        compMat.name = "Blech"
        val compMat2 = Material();
        compMat2.name = "Pfannenwender"
        val compSet = mutableSetOf<Material>(compMat, compMat2)

        assertEquals(compMat.name, retSet.elementAt(0).name);
        assertEquals(compMat2.name, retSet.elementAt(1).name);
    }

    @Test
    fun apiToIngredientTest() {
        var apiIng = ApiIng()
        apiIng.amount = "500g"
        apiIng.name = "Pommes"

        var inputAl =  arrayListOf<ApiIng>(apiIng)
        var retSet = Converter.apiToIngredient(inputAl)

        val compIng1 = Ingredient();
        compIng1.name = "Pommes"
        compIng1.amount = "500g"

        assertEquals(compIng1.name, retSet.elementAt(0).name);
        assertEquals(compIng1.amount, retSet.elementAt(0).amount);
    }

    @Test
    fun apiToMaterialTest() {
        var apiMat = ApiMat()
        apiMat.name = "Backblech"

        var inputAL = arrayListOf<ApiMat>(apiMat)
        var retSet = Converter.apiToMaterial(inputAL)

        val compMat = Material();
        compMat.name = "Backblech"

        assertEquals(compMat.name, retSet.elementAt(0));
    }

    @Test
    fun ingToMutableSetTest() {
        val ing = Ingredient();
        ing.name = "Pommes"
        ing.amount = "500g"
        val ingEmptyName = Ingredient();
        ingEmptyName.name = ""
        ingEmptyName.amount = "1kg"
        val ingEmptyAmount = Ingredient();
        ingEmptyAmount.name = "Kartoffeln"
        ingEmptyAmount.amount = ""
        val inputSet = setOf(ing,ingEmptyAmount,ingEmptyName)

        val retSet = Converter.ingToMutableSet(inputSet)

        assertEquals(ing.name, retSet.elementAt(0).elementAt(0));
        assertEquals(ing.amount, retSet.elementAt(0).elementAt(1));
        assertEquals(1, retSet.size);
    }

    @Test
    fun matToMutableSetTest() {
        val mat = Material();
        mat.name = "Blech"
        val matEmpty = Material();
        matEmpty.name = ""
        val inputSet = setOf(mat, matEmpty)

        val retSet = Converter.matToMutableSet(inputSet)

        assertEquals(mat.name, retSet.elementAt(0));
        assertEquals(1, retSet.size);
    }
}