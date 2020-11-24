package com.jrk.mood4food

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.jrk.mood4food.recipes.selection.view.SelectionActivity
import com.jrk.mood4food.waterbalance.view.WaterBalanceActivity

open class NavBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Navigate to Waterbalance UI
        findViewById<LinearLayout>(R.id.water_nav_bar).setOnClickListener{
            //setContentView(R.layout.activity_water_balance)
            startActivity(Intent(this, WaterBalanceActivity::class.java))
        }

        //Navigate to Nutrietns UI
        findViewById<LinearLayout>(R.id.nutrients).setOnClickListener{
            //setContentView(R.layout.activity_nutrients)
            startActivity(Intent(this,NutrientActivity::class.java))
        }

        //Navigate to Settings UI
        findViewById<LinearLayout>(R.id.settings).setOnClickListener{
            //setContentView(R.layout.activity_settings)
            startActivity(Intent(this,SettingsActivity::class.java))
        }

        //Navigate to Recipes Selection UI
        findViewById<LinearLayout>(R.id.recipes).setOnClickListener{
            //setContentView(R.layout.activity_recipes)
            startActivity(Intent(this, SelectionActivity::class.java))
        }

        //Navigate to Home UI
        findViewById<LinearLayout>(R.id.home).setOnClickListener{
            //setContentView(R.layout.activity_home)
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}