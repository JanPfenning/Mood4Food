package com.jrk.mood4food.settings.view

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.jrk.mood4food.NavBarActivity
import com.jrk.mood4food.R
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.waterbalance.controller.SettingsControler
import com.jrk.mood4food.waterbalance.model.SettingsObserver
import com.jrk.mood4food.waterbalance.view.SettingsView
import org.w3c.dom.Text

class SettingsActivity : NavBarActivity(), SettingsView, SettingsObserver {
    private val model = ModelModule.dataAccessLayer
    private val controller = SettingsControler(model)

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_settings)
        controller.bind(this)
        findViewById<LinearLayout>(R.id.calcNeeds).setOnClickListener{
            startActivity(Intent(this, CalculationNeedsActivty::class.java))
        }
        findViewById<LinearLayout>(R.id.editGoals).setOnClickListener(){
            startActivity(Intent(this, EditGoalsActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.setIngredients).setOnClickListener(){
            startActivity(Intent(this, SetIngredientsActivity::class.java))
        }

        super.onCreate(savedInstanceState)


    }
    override fun onStart() {
        super.onStart()
        model.register(this)
    }


    override fun onStop() {
        super.onStop()
        model.unregister(this)
    }



    override fun calculationOfNeedsDone() {}
}