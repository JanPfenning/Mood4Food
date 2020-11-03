package com.jrk.mood4food

import android.os.Bundle

class RecipesActivity : NavBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_recipes)
        super.onCreate(savedInstanceState)
        println("open recipes")
    }
}