package com.jrk.mood4food.recipes.add_mod

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.jrk.mood4food.R
import com.jrk.mood4food.Tuple

class IngredientAdapter(private var ingredients: Array<Set<String>>,
                        private val context: Activity) : ArrayAdapter<Set<String>>(context, R.layout.activity_edit_recipe, ingredients) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.adapter_edit_ingredient, null, true)

        val ingredient_name = rowView.findViewById(R.id.input_ingredient) as TextView
        val ingredient_amount = rowView.findViewById(R.id.input_amount) as TextView
        val remove_ingredient = rowView.findViewById(R.id.remove_ingredient) as ImageView

        ingredient_name.text = ingredients[position].elementAt(0)
        ingredient_amount.text= ingredients[position].elementAt(1)
        //TODO make ingredient removable (same issue at material adapter)
        remove_ingredient.setOnClickListener({

        })

        return rowView
    }
}
