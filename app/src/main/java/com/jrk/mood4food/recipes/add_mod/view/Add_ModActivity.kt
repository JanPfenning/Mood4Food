package com.jrk.mood4food.recipes.add_mod.view

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.jrk.mood4food.App
import com.jrk.mood4food.R
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.recipes.MODE
import com.jrk.mood4food.recipes.add_mod.*
import com.jrk.mood4food.recipes.add_mod.controller.Add_ModController
import com.jrk.mood4food.recipes.add_mod.model.Add_ModObserver
import com.jrk.mood4food.recipes.detail.model.RecipeEntity
import com.jrk.mood4food.recipes.detail.view.DetailActivity
import com.jrk.mood4food.recipes.selection.view.SelectionActivity


class Add_ModActivity : AppCompatActivity(), Add_ModView, Add_ModObserver {
    private val model = ModelModule.dataAccessLayer
    private val controller = Add_ModController(model)
    private var mode = MODE.NEW
    private var imageUri = Uri.EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_edit_recipe)
        super.onCreate(savedInstanceState)
        controller.bind(this)

        if (intent.hasExtra("recipe_id")) {
            mode = MODE.EDIT;
        }


        var recipe = RecipeEntity(App.getContext())
        var ingredients: MutableSet<Ingredient> = mutableSetOf()
        var materials: MutableSet<Material> = mutableSetOf()

        val ingredientsList = findViewById<ListView>(R.id.mod_ingredient_list)
        val materialsList = findViewById<ListView>(R.id.mod_materials_list)
        val titleView = findViewById<TextView>(R.id.modify_recipe_name)
        val descriptionView = findViewById<TextView>(R.id.modify_description);
        val imageView = findViewById<ImageView>(R.id.imageView)

        // If not new Recipe, read existing data, which is to be updated
        if (mode == MODE.EDIT) {
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
            ingredients = Converter.setToIngredient(recipe.ingredients)
            if (recipe.imageUri != "") {
                imageUri = Uri.parse(recipe.imageUri)
            }
            materials = Converter.setToMaterials(recipe.materials)
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
            imageView.setImageURI(imageUri)

            // On delete recipe
            findViewById<Button>(R.id.delete_recipe).setOnClickListener {
                val builder = AlertDialog.Builder(this)

                builder.setTitle("Confirm")
                builder.setMessage("Are you sure?")

                builder.setPositiveButton("YES") { dialog, _ -> // Do nothing but close the dialog
                    removeRecipe(recipe)
                    dialog.dismiss()
                }

                builder.setNegativeButton("NO") { dialog, _ -> // Do nothing
                    dialog.dismiss()
                }

                val alert = builder.create()
                alert.show()
            }
        }

        // On confirm (Save button):
        findViewById<ImageView>(R.id.confirm).setOnClickListener {
            recipe.title = titleView.text.toString()
            recipe.ingredients = Converter.ingToMutableSet(ingredients)
            recipe.materials = Converter.matToMutableSet(materials)
            recipe.description = descriptionView.text.toString()
            recipe.imageUri = imageUri.toString()

            controller.onSave(recipe)

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("id", recipe.storageAddress)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent)
        }

        // On upload picutre:
        findViewById<TextView>(R.id.upload_picture).setOnClickListener {
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED) {
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    //permission already granted
                    pickImageFromGallery();
                }
            } else {
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }

        // On cancle modification
        findViewById<ImageView>(R.id.cancel_modify_recipe).setOnClickListener {
            if (mode == MODE.NEW) {
                intent = Intent(this, SelectionActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent)
            } else if (mode == MODE.EDIT) {
                val intent = Intent(this, DetailActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id", recipe.storageAddress)
                startActivity(intent)
            }
        }

        findViewById<ImageView>(R.id.add_ingredient).setOnClickListener {
            ingredients.add(Ingredient())
            ingredientsList.adapter = IngredientAdapter(
                    ingredients.toTypedArray(),
                    this
            )
            (ingredientsList.adapter as IngredientAdapter).notifyDataSetChanged()
        }

        findViewById<ImageView>(R.id.add_material).setOnClickListener {
            materials.add(Material())
            materialsList.adapter = MaterialAdapter(
                    materials.toTypedArray(),
                    this
            )
            (materialsList.adapter as MaterialAdapter).notifyDataSetChanged()
        }
    }

    /**
     * Called after Successfull passing the deletion confirmation dialog
     *
     * */
    private fun removeRecipe(recipe: RecipeEntity) {
        controller.removeRecipe(recipe)

        val intent = Intent(this, SelectionActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
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

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            findViewById<ImageView>(R.id.imageView).setImageURI(data?.data)
            data?.data?.let {
                contentResolver.takePersistableUriPermission(it,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION)
                this.imageUri = data.data
            };
        }
    }

}