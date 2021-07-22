package com.jrk.mood4food.recipes.detail.view

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.jrk.mood4food.App
import com.jrk.mood4food.NavBarActivity
import com.jrk.mood4food.R
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.model.api.endpoints.RecipeEndpoint
import com.jrk.mood4food.model.api.entity.Recipe
import com.jrk.mood4food.recipes.add_mod.Converter
import com.jrk.mood4food.recipes.add_mod.view.Add_ModActivity
import com.jrk.mood4food.recipes.detail.IngredientAdapter
import com.jrk.mood4food.recipes.detail.controller.DetailController
import com.jrk.mood4food.recipes.detail.model.DetailObserver
import com.jrk.mood4food.recipes.detail.model.RecipeEntity
import java.net.URL
import kotlin.reflect.KFunction1


class DetailActivity : NavBarActivity(), DetailView, DetailObserver {
    private val model = ModelModule.dataAccessLayer
    private val controller = DetailController(model)

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_read_recipe)
        super.onCreate(savedInstanceState)
        controller.bind(this)

        val id = intent.getStringExtra("id")

        var recipe:RecipeEntity
        if (id !is String){
            Log.e("RECIPE","id is not there")
        }else{
            val api = intent.getBooleanExtra("api", false)
            if(api){
                val id = id.toInt();
                RecipeEndpoint.get(App.getContext(), this::onApiResult as KFunction1<Any, Unit>, id)
            }else
            {
                recipe = model.getRecipeRepository().loadRecipeDetails(id)
                fillData()
                findViewById<ImageView>(R.id.edit_recipe).setOnClickListener{
                    val intent = Intent(this, Add_ModActivity::class.java);
                    intent.putExtra("recipe_id",recipe.storageAddress)
                    startActivity(intent);
                }
            }

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
                finish()
            }
        }
    }

    //Helper function to toggle the visibility of the content and the corresponding button
    fun toggle(content:View,button:ImageView){
        if(content.visibility==View.VISIBLE){
            button.setImageResource(R.drawable.ic_baseline_navigate_next_24)
            content.visibility=View.GONE
        }else{
            button.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
            content.visibility=View.VISIBLE
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

    override fun onResume(){
        super.onResume()
        val recipe = model.getRecipeRepository().loadRecipeDetails(intent.getStringExtra("id"))
        val api = intent.getBooleanExtra("api", false)
        if(!api){
            findViewById<ImageView>(R.id.recipe_pic).setImageURI(Uri.parse(recipe.imageUri))
            fillData()
        }
    }

    fun fillData(){
        val recipe = model.getRecipeRepository().loadRecipeDetails(intent.getStringExtra("id"))
        //Filling the Recipe Data into the Views
        /*Title*/
        findViewById<TextView>(R.id.recipe_title).text = recipe.title
        /*Picture*/
        findViewById<ImageView>(R.id.recipe_pic).setImageURI(Uri.parse(recipe.imageUri))
        /*Fav*/
        drawFav(recipe)
        findViewById<ImageView>(R.id.noFav).setOnClickListener{
            toggleFav(recipe)
            drawFav(recipe)
        }
        findViewById<ImageView>(R.id.Fav).setOnClickListener{
            toggleFav(recipe)
            drawFav(recipe)
        }
        /*Ingredients*/
        val ingredientView = findViewById<ListView>(R.id.ingredient_list)
        ingredientView.adapter = IngredientAdapter(
                Converter.setToIngredient(recipe.ingredients).toTypedArray(),
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

    fun drawFav(recipe: RecipeEntity){
        if(recipe.favorite){
            findViewById<ImageView>(R.id.noFav).visibility = View.GONE
            findViewById<ImageView>(R.id.Fav).visibility = View.VISIBLE
        }else{
            findViewById<ImageView>(R.id.noFav).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.Fav).visibility = View.GONE
        }
    }

    fun toggleFav(recipe: RecipeEntity){
        recipe.favorite = !recipe.favorite
        controller.updateFav(recipe)
    }

    fun onApiResult(recipe: Recipe){
        findViewById<ImageView>(R.id.edit_recipe).visibility = View.GONE

        //Filling the Recipe Data into the Views
        /*Title*/
        findViewById<TextView>(R.id.recipe_title).text = recipe.title
        /*Picture*/
        val thread = Thread(Runnable {
            try {
                val thumb_u = URL(recipe.imageUri)
                val thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src")
                findViewById<ImageView>(R.id.recipe_pic).setImageDrawable(thumb_d)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Log.e("IMAGE",e.toString())
            }
        })
        thread.start()
        /*Fav*/
        findViewById<ImageView>(R.id.noFav).visibility = View.GONE;
        findViewById<ImageView>(R.id.Fav).visibility = View.GONE;
        /*drawFav(recipe)
        findViewById<ImageView>(R.id.noFav).setOnClickListener{
            toggleFav(recipe)
            drawFav(recipe)
        }
        findViewById<ImageView>(R.id.Fav).setOnClickListener{
            toggleFav(recipe)
            drawFav(recipe)
        }*/
        /*Ingredients*/
        val ingredientView = findViewById<ListView>(R.id.ingredient_list)
        ingredientView.adapter = IngredientAdapter(
                Converter.apiToIngredient(recipe.ingredients).toTypedArray(),
                this)
        /*Materials*/
        val materialsView = findViewById<ListView>(R.id.materials_list)
        materialsView.adapter = ArrayAdapter<String>(
                App.getContext(),
                R.layout.adapter_read_item,
                R.id.item_name,
                Converter.apiToMaterial(recipe.materials).toTypedArray())
        /*Description*/
        findViewById<TextView>(R.id.description_content).text = recipe.description
    }
}