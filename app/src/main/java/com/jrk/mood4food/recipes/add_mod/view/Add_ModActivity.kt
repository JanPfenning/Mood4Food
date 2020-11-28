package com.jrk.mood4food.recipes.add_mod.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.storage.StorageManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.jrk.mood4food.*
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.model.localStorage.LocalEntity
import com.jrk.mood4food.recipes.MODE
import com.jrk.mood4food.recipes.add_mod.Ingredient
import com.jrk.mood4food.recipes.add_mod.controller.Add_ModController
import com.jrk.mood4food.recipes.add_mod.model.Add_ModObserver
import com.jrk.mood4food.recipes.add_mod.IngredientAdapter
import com.jrk.mood4food.recipes.add_mod.Material
import com.jrk.mood4food.recipes.add_mod.MaterialAdapter
import com.jrk.mood4food.recipes.detail.model.RecipeEntity
import com.jrk.mood4food.recipes.detail.view.DetailActivity
import com.jrk.mood4food.recipes.selection.view.SelectionActivity


class Add_ModActivity : AppCompatActivity(), Add_ModView, Add_ModObserver {
    private val model = ModelModule.dataAccessLayer
    private val controller = Add_ModController(model)
    private var mode = MODE.NEW

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_edit_recipe)
        super.onCreate(savedInstanceState)
        controller.bind(this)

        if(intent.hasExtra("recipe_id")){
            mode = MODE.EDIT;
        }

        var recipe = RecipeEntity(App.getContext())
        var ingredients : MutableSet<Ingredient> = mutableSetOf()
        var materials : MutableSet<Material> = mutableSetOf()

        val ingredientsList = findViewById<ListView>(R.id.mod_ingredient_list)
        val materialsList = findViewById<ListView>(R.id.mod_materials_list)
        val titleView = findViewById<TextView>(R.id.modify_recipe_name)
        val descriptionView = findViewById<TextView>(R.id.modify_description);

        if(mode == MODE.EDIT){
            recipe = model.getRecipeRepository().loadRecipeDetails(intent.getStringExtra("recipe_id")!!)
            titleView.text = recipe.title
            titleView.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    recipe.title = s.toString()
                }
            })
            ingredients = model.getRecipeRepository().setToIngredient(recipe.ingredients)
            materials = model.getRecipeRepository().setToMaterials(recipe.materials)
            descriptionView.text = recipe.description
            descriptionView.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    recipe.description = s.toString()
                }
            })

            ingredientsList.adapter = IngredientAdapter(
                    ingredients.toTypedArray(),
                    this
            )
            materialsList.adapter = MaterialAdapter(
                    materials.toTypedArray(),
                    this
            )

        }


        findViewById<ImageView>(R.id.confirm).setOnClickListener{
            recipe.title = titleView.text.toString()
            recipe.ingredients = model.getRecipeRepository().ingToMutableSet(ingredients)
            recipe.materials = model.getRecipeRepository().matToMutableSet(materials)
            recipe.description = descriptionView.text.toString()

            //TODO Save via controller
            recipe.saveToLocalStorage(recipe)
            //controller.onSave(recipe)

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("id",recipe.storageAddress)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.upload_picture).setOnClickListener{
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }

        findViewById<ImageView>(R.id.cancel_modify_recipe).setOnClickListener{
            if(mode == MODE.NEW){
                startActivity(Intent(this, SelectionActivity::class.java))
            }else if(mode == MODE.EDIT){
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("id",recipe.storageAddress)
                startActivity(intent)
            }
        }

        findViewById<ImageView>(R.id.add_ingredient).setOnClickListener{
            ingredients.add(Ingredient())
            ingredientsList.adapter = IngredientAdapter(
                    ingredients.toTypedArray(),
                    this
            )
            (ingredientsList.adapter as IngredientAdapter).notifyDataSetChanged()
        }

        findViewById<ImageView>(R.id.add_material).setOnClickListener{
            materials.add(Material())
            materialsList.adapter = MaterialAdapter(
                    materials.toTypedArray(),
                    this
            )
            (materialsList.adapter as MaterialAdapter).notifyDataSetChanged()
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
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

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            findViewById<ImageView>(R.id.imageView).setImageURI(data?.data)
        }
    }

}