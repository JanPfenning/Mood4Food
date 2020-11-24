package com.jrk.mood4food.recipes.selection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jrk.mood4food.R
import com.jrk.mood4food.recipes.detail.model.RecipeEntity
import com.jrk.mood4food.recipes.selection.view.RecipeClickListener

class RecipeAdapter(private val dataSet: Array<RecipeEntity>,
                    private val recipeClickListener: RecipeClickListener) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        //Define Views which are used in "onBindViewHolder"
        val textView = view.findViewById<TextView>(R.id.recipe_card_title)
        val cardView = view.findViewById<androidx.cardview.widget.CardView>(R.id.recipe_card)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Load Adapter
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.adapter_recipe, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Put data into the views defined by viewHolder
        viewHolder.textView.text = dataSet[position].title
        viewHolder.cardView.setOnClickListener{
            recipeClickListener.onRecipeClickListener(dataSet[position].storageAddress.toString())
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}
