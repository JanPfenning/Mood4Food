package com.jrk.mood4food

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Navigate to Waterbalance UI
        findViewById<LinearLayout>(R.id.water_nav_bar).setOnClickListener{
            setContentView(R.layout.activity_water_balance)
        }

        //Navigate to Nutrietns UI
        findViewById<LinearLayout>(R.id.nutrients).setOnClickListener{
            setContentView(R.layout.activity_nutrients)
        }

        //Navigate to Settings UI
        findViewById<LinearLayout>(R.id.settings).setOnClickListener{
            setContentView(R.layout.activity_settings)
        }

        //Navigate to Recipes UI
        findViewById<LinearLayout>(R.id.recipes).setOnClickListener{
            setContentView(R.layout.activity_recipes)
        }

        //Navigate to Home UI
        findViewById<LinearLayout>(R.id.home).setOnClickListener{
            setContentView(R.layout.activity_home)
        }

    }
}