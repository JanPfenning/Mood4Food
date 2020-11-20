package com.jrk.mood4food.recipes.add_mod

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.jrk.mood4food.R
import com.jrk.mood4food.Tuple

class MaterialAdapter(private var materials: Array<String>,
                      private val context: Activity) : ArrayAdapter<String>(context, R.layout.activity_edit_recipe, materials) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.adapter_edit_item, null, true)

        val material_name = rowView.findViewById(R.id.input_item_name) as TextView
        val remove_material = rowView.findViewById(R.id.remove_material) as ImageView

        material_name.text = materials[position]
        //TODO make Material removable (same issue at ingredient adapter)
        remove_material.setOnClickListener({

        })

        return rowView
    }
}
