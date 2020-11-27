package com.jrk.mood4food.recipes.add_mod

import android.app.Activity
import android.app.LauncherActivity.ListItem
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import com.jrk.mood4food.R


class IngredientAdapter(private var ingredients: Array<Ingredient>,
                        private val context: Activity) : ArrayAdapter<Ingredient>(context, R.layout.activity_edit_recipe, ingredients) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.adapter_edit_ingredient, null, true)

        val ingredient_name = rowView.findViewById(R.id.input_ingredient) as EditText
        val ingredient_amount = rowView.findViewById(R.id.input_amount) as EditText
        val remove_ingredient = rowView.findViewById(R.id.remove_ingredient) as ImageView

        val listItem = getItem(position)

        ingredient_name.setText(ingredients[position].name)
        ingredient_amount.setText(ingredients[position].amount)

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

        //Update Amount on change
        ingredient_amount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listItem!!.amount = s.toString()
            }
        })

        remove_ingredient.setOnClickListener({

        })

        return rowView
    }
}
