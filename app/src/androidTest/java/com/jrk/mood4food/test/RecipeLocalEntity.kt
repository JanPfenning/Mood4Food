package com.jrk.mood4food.test

import com.jrk.mood4food.App
import com.jrk.mood4food.recipes.detail.model.RecipeEntity
import com.jrk.mood4food.recipes.detail.view.DetailActivity
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class RecipeLocalEntity {

    @Test fun initValueRecipe() {
        val mock_recipe: RecipeEntity = RecipeEntity(App.getContext())
        assertEquals("", mock_recipe.title);
        assertEquals(0, mock_recipe.ingredients.size);
        assertEquals(0, mock_recipe.materials.size);
        assertEquals("", mock_recipe.description);
        assertEquals("", mock_recipe.imageUri);
        assertEquals(false, mock_recipe.favorite);
        assertEquals("", mock_recipe.lastEaten);
    }

    @Test fun setAndToggleFavorite() {
        val mock_recipe: RecipeEntity = RecipeEntity(App.getContext())
        mock_recipe.favorite = true;
        assertEquals(true, mock_recipe.favorite);
        mock_recipe.favorite = !mock_recipe.favorite
        assertEquals(false, mock_recipe.favorite);
        mock_recipe.favorite = !mock_recipe.favorite
        assertEquals(true, mock_recipe.favorite);
        mock_recipe.favorite = false;
        assertEquals(false, mock_recipe.favorite);
    }
}