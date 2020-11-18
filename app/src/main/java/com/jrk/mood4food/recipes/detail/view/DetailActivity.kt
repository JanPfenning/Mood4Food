package com.jrk.mood4food.recipes.detail.view

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.jrk.mood4food.*
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.model.localStorage.LocalStorage
import com.jrk.mood4food.recipes.detail.controller.DetailController
import com.jrk.mood4food.recipes.detail.model.DetailObserver
import com.jrk.mood4food.recipes.detail.model.RecipeEntity
import com.jrk.mood4food.recipes.selection.view.SelectionActivity


class DetailActivity : NavBarActivity(), DetailView, DetailObserver {
    private val model = ModelModule.dataAccessLayer
    private val controller = DetailController(model)

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_read_recipe)
        super.onCreate(savedInstanceState)
        controller.bind(this)

        //var id = intent.getStringExtra("id")

        //Testcode
        var testRecipe = RecipeEntity(App.getContext())
        testRecipe.title = "Test Rezept"
        testRecipe.ingredients = setOf("1","2","3")
        testRecipe.materials = setOf("1","2","3")
        testRecipe.description = "Schritt für Schritt Anleitung"
        testRecipe.saveToLocalStorage(testRecipe)
        var id = testRecipe.storageAddress //TODO this id should be given to the activity via parameter

        var recipe:RecipeEntity
        if (id !is String){
            Log.e("RECIPE","id is not there")
        }else{
            recipe = model.getRecipeRepository().loadRecipeDetails(id)

            //Initialization of on-click-listeners for views
            var toggleIngredients:View.OnClickListener = View.OnClickListener {
                toggle(findViewById(R.id.ingredient_list),findViewById(R.id.expand_ingredients))
            }
            var toggleMaterials:View.OnClickListener = View.OnClickListener {
                toggle(findViewById(R.id.materials_list),findViewById(R.id.expand_materials))
            }
            var toggleDescription:View.OnClickListener = View.OnClickListener {
                toggle(findViewById(R.id.description_content),findViewById(R.id.expand_description))
            }

            //Adding the Listeners to the Views
            findViewById<ImageView>(R.id.expand_ingredients).setOnClickListener(toggleIngredients)
            findViewById<TextView>(R.id.ingredient_header).setOnClickListener(toggleIngredients)
            findViewById<ImageView>(R.id.expand_materials).setOnClickListener(toggleMaterials)
            findViewById<TextView>(R.id.materials_header).setOnClickListener(toggleMaterials)
            findViewById<ImageView>(R.id.expand_description).setOnClickListener(toggleDescription)
            findViewById<TextView>(R.id.description_header).setOnClickListener(toggleDescription)

            //Backnavigation
            findViewById<ImageView>(R.id.back).setOnClickListener {
                startActivity(Intent(this, SelectionActivity::class.java))
            }

            //Filling the Recipe Data into the Views
            /*Title*/
            findViewById<TextView>(R.id.recipe_title).text = recipe.title
            /*Ingredients*/
            //TODO Adapter
            //var adapter = ArrayAdapter(this, R.layout.adapter_read_ingredient, arrayOf(recipe.ingredients))
            //findViewById<ListView>(R.id.ingredient_list).adapter = adapter
            /*Materials*/
            //TODO Adapter
            //var adapter = ArrayAdapter(this, R.layout.adapter_read_item, arrayOf(recipe.materials))
            //findViewById<ListView>(R.id.materials_list).adapter = adapter
            /*Description*/
            findViewById<TextView>(R.id.description_content).text = recipe.description
        }
    }

    //Helper function to toggle the visibility of the content and the corresponding button
    fun toggle(content:View,button:ImageView){
        if(content.visibility==View.VISIBLE){
            content.visibility=View.GONE
            button.setBackgroundResource(R.drawable.ic_baseline_navigate_next_24)
        }else{
            content.visibility=View.VISIBLE
            button.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
        }
    }

    override fun onStart() {
        super.onStart()
        model.register(this)
    }


    override fun onStop() {
        super.onStop()
        model.unregister(this)
    }

}