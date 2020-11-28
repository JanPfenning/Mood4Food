package com.jrk.mood4food.settings.view

import android.os.Bundle
import android.widget.TextView
import com.jrk.mood4food.NavBarActivity
import com.jrk.mood4food.R
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.waterbalance.controller.SettingsControler
import com.jrk.mood4food.waterbalance.model.SettingsObserver
import com.jrk.mood4food.waterbalance.view.SettingsView

class SettingsActivity : NavBarActivity(), SettingsView, SettingsObserver {
    private val model = ModelModule.dataAccessLayer
    private val controller = SettingsControler(model)

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_settings)
        controller.bind(this)
        findViewById<TextView>(R.id.calcNeeds).setOnClickListener{
            setContentView(R.layout.activity_goals)
            findViewById<TextView>(R.id.caculateNeeds).setOnClickListener{
                controller.calculateNeeds()
            }

        }
        super.onCreate(savedInstanceState)

    }
}