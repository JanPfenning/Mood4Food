package com.jrk.mood4food.settings.view

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import com.jrk.mood4food.R


class IngredientAdapterSettings(private var ingredients: Array<IngredientSettings>,
                                private val context: Activity) : ArrayAdapter<IngredientSettings>(context, R.layout.activity_edit_recipe, ingredients) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.adapter_add_ingredient_settings, null, true)

        val ingredient_name = rowView.findViewById(R.id.input_ingredient) as EditText
        val remove_ingredient = rowView.findViewById(R.id.remove_ingredient) as ImageView

        val listItem = getItem(position)

        ingredient_name.setText(ingredients[position].name)

        //Update Name on Change
        ingredient_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listItem!!.name = s.toString()
            }
        })



        remove_ingredient.setOnClickListener{

            listItem!!.name = ""
            ingredient_name.setText(listItem.name)
        }

        return rowView
    }
}
