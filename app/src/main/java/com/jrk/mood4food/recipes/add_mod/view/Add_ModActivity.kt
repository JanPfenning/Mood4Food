package com.jrk.mood4food.recipes.add_mod.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jrk.mood4food.*
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.recipes.add_mod.controller.Add_ModController
import com.jrk.mood4food.recipes.add_mod.model.Add_ModObserver
import com.jrk.mood4food.recipes.add_mod.IngredientAdapter
import com.jrk.mood4food.recipes.add_mod.MaterialAdapter
import com.jrk.mood4food.recipes.detail.model.RecipeEntity
import com.jrk.mood4food.recipes.detail.view.DetailActivity
import com.jrk.mood4food.recipes.selection.view.SelectionActivity


class Add_ModActivity : AppCompatActivity(), Add_ModView, Add_ModObserver {
    private val model = ModelModule.dataAccessLayer
    private val controller = Add_ModController(model)

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_edit_recipe)
        super.onCreate(savedInstanceState)
        controller.bind(this)

        //TODO fill recipe data with the input fields on read
        var recipe = RecipeEntity(App.getContext())

        findViewById<ImageView>(R.id.confirm).setOnClickListener{
            controller.onSave(recipe)
        }

        findViewById<TextView>(R.id.upload_picture).setOnClickListener{
            //TODO on clicking upload picture
        }

        findViewById<ImageView>(R.id.cancel_modify_recipe).setOnClickListener{
            startActivity(Intent(this, SelectionActivity::class.java))
        }

        //TODO save data with input fields
        var ingredients : MutableSet<Set<String>> = mutableSetOf()
        findViewById<ImageView>(R.id.add_ingredient).setOnClickListener{
            ingredients.add(setOf("","_"))
            var listView = findViewById<ListView>(R.id.mod_ingredient_list)
            listView.adapter = IngredientAdapter(
                    ingredients.toTypedArray(),
                    this
            )
            (listView.adapter as IngredientAdapter).notifyDataSetChanged()
        }

        //TODO save data with input fields
        var materials : MutableSet<String> = mutableSetOf()
        findViewById<ImageView>(R.id.add_material).setOnClickListener{
            materials.add("")
            var listView = findViewById<ListView>(R.id.mod_materials_list)
            listView.adapter = MaterialAdapter(
                    materials.toTypedArray(),
                    this
            )
            (listView.adapter as MaterialAdapter).notifyDataSetChanged()
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

    //TODO Test this code, maybe recipeEntity is not given through DataAcessLayer
    override fun recipeSaved(recipeEntity: RecipeEntity) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("id",recipeEntity.storageAddress)
        startActivity(intent)
    }

}