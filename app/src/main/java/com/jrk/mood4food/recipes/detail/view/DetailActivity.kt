package com.jrk.mood4food.recipes.detail.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.jrk.mood4food.*
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.recipes.detail.IngredientAdapter
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

        val id = intent.getStringExtra("id")

        val recipe:RecipeEntity
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
            val ingredientView = findViewById<ListView>(R.id.ingredient_list)
            //TODO change the Arrayadapter to custom adapter that uses adapter_read_ingredient
            ingredientView.adapter = IngredientAdapter(
                    recipe.ingredients.toTypedArray(),
                    this)
            /*Materials*/
            val materialsView = findViewById<ListView>(R.id.materials_list)
            materialsView.adapter = ArrayAdapter<String>(
                    App.getContext(),
                    R.layout.adapter_read_item,
                    R.id.item_name,
                    recipe.materials.toTypedArray())
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