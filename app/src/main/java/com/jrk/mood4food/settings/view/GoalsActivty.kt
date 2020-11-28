package com.jrk.mood4food.settings.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.jrk.mood4food.NavBarActivity
import com.jrk.mood4food.R
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.settings.controller.GoalController
import com.jrk.mood4food.waterbalance.controller.SettingsControler
import com.jrk.mood4food.waterbalance.model.SettingsObserver
import com.jrk.mood4food.waterbalance.view.SettingsView

class GoalsActivty: NavBarActivity(), SettingsObserver, GoalView {
    private val model = ModelModule.dataAccessLayer
    private val controller = GoalController(model)

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_goals)
        controller.bind(this)
        findViewById<TextView>(R.id.caculateNeeds).setOnClickListener{
            controller.calculateNeeds()
        }
        findViewById<ImageView>(R.id.backToSettings).setOnClickListener{
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        super.onCreate(savedInstanceState)

    }
    override fun calculationOfNeedsDone() {
        var data = model.getSettingsRepository().getCalculatedNeeds()
        findViewById<TextView>(R.id.waterPerDay).text = data.waterPerDay.toString()
        findViewById<TextView>(R.id.caloriesPerDay).text = data.caloriesPerDay.toString()
        findViewById<TextView>(R.id.proteinPerDay).text = data.proteinPerDay.toString()
        findViewById<TextView>(R.id.carbohydratesPerDay).text = data.carbohydratesPerDay.toString()
        findViewById<TextView>(R.id.fatPerDay).text = data.fatPerDay.toString()
    }

    override fun getCalculationData(): SettingsPhysicalConditionData {
        var settingsPhysicalConditionData = SettingsPhysicalConditionData()
        settingsPhysicalConditionData.actualBodyWeight = findViewById<TextView>(R.id.actualBodyWeight).text.toString().toFloat()
        settingsPhysicalConditionData.aimBodyWeight = findViewById<TextView>(R.id.aimBodyWeight).text.toString().toFloat()
        //settingsPhysicalConditionData.weightChange = findViewById<androidx.appcompat.widget.AppCompatSpinner>(R.id.weightChange).texttoString().toFloat()
        settingsPhysicalConditionData.weightChangePerMonth = findViewById<TextView>(R.id.weightChangePerMonth).text.toString().toFloat()
        //settingsPhysicalConditionData.physicalActivity = findViewById<TextView>(R.id.physicalActivity).text.toString()

        return settingsPhysicalConditionData
    }
    override fun onStart() {
        super.onStart()
        model.register(this)
    }


    override fun onStop() {
        super.onStop()
        model.unregister(this)
    }
}