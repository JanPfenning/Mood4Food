package com.jrk.mood4food

import android.os.Bundle

class NutrientActivity : NavBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_nutrients)
        super.onCreate(savedInstanceState)
        println("opened nutrients")
    }
}