package com.jrk.mood4food.recipes.selection

import android.app.Activity
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jrk.mood4food.R
import com.jrk.mood4food.recipes.detail.model.RecipeEntity
import com.jrk.mood4food.recipes.selection.view.RecipeClickListener
import java.net.URL

class RecipeAdapter(private val dataSet: Array<RecipeEntity>,
                    private val recipeClickListener: RecipeClickListener,
                    private val api: Boolean,
                    private val activity: Activity) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        //Define Views which are used in "onBindViewHolder"
        val textView = view.findViewById<TextView>(R.id.recipe_card_title)
        val cardView = view.findViewById<androidx.cardview.widget.CardView>(R.id.recipe_card)
        val imageView = view.findViewById<ImageView>(R.id.recipe_card_image)
        val favView = view.findViewById<ImageView>(R.id.Fav)
        val nofavView = view.findViewById<ImageView>(R.id.noFav)

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
            if(api){
                recipeClickListener.onRecipeClickListener(dataSet[position].APIid.toString(), api)
            }else{
                recipeClickListener.onRecipeClickListener(dataSet[position].storageAddress.toString(), api)
            }
        }
        if(api){
            val thread = Thread(Runnable {
                val thumb_u = URL(dataSet[position].imageUri)
                val thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src")
                activity.runOnUiThread(java.lang.Runnable {
                    try {
                        viewHolder.imageView.setImageDrawable(thumb_d)
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                        Log.e("IMAGE",e.toString()+dataSet[position].imageUri)
                    }
                })
            })
            thread.start()
        }
        else{
            viewHolder.imageView.setImageURI(Uri.parse(dataSet[position].imageUri))
        }
        if(dataSet[position].favorite){
            viewHolder.nofavView.visibility = View.GONE
        }else{
            viewHolder.favView.visibility = View.GONE
        }

        if(api){
            viewHolder.nofavView.visibility = View.GONE
            viewHolder.favView.visibility = View.GONE
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}
