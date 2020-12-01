package com.jrk.mood4food.recipes.add_mod

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.jrk.mood4food.R
import com.jrk.mood4food.Tuple

class MaterialAdapter(private var materials: Array<Material>,
                      private val context: Activity) : ArrayAdapter<Material>(context, R.layout.activity_edit_recipe, materials) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.adapter_edit_item, null, true)

        val material_name = rowView.findViewById(R.id.input_item_name) as EditText
        val remove_material = rowView.findViewById(R.id.remove_material) as ImageView

        val listItem = getItem(position)
        material_name.setText(materials[position].name)

        material_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listItem!!.name = s.toString()
            }
        })

        remove_material.setOnClickListener{
            listItem!!.name = ""
        }

        return rowView
    }
}
