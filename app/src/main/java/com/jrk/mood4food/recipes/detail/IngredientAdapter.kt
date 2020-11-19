package com.jrk.mood4food.recipes.detail

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.jrk.mood4food.R
import com.jrk.mood4food.Tuple

class IngredientAdapter(private val ingredients: Array<Set<String>>,
                        private val context: Activity) : ArrayAdapter<Set<String>>(context, R.layout.activity_read_recipe, ingredients) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.adapter_read_ingredient, null, true)

        val ingredient_name = rowView.findViewById(R.id.ingredient_name) as TextView
        val ingredient_amount = rowView.findViewById(R.id.ingredient_amount) as TextView

        ingredient_name.text = ingredients[position].elementAt(0)
        ingredient_amount.text= ingredients[position].elementAt(1)

        return rowView
    }
}
