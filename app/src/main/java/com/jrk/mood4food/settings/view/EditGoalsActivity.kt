package com.jrk.mood4food.settings.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.jrk.mood4food.NavBarActivity
import com.jrk.mood4food.R
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.settings.controller.EditGoalsController
import com.jrk.mood4food.waterbalance.model.SettingsEntity

class EditGoalsActivity  : NavBarActivity(){
    private val model = ModelModule.dataAccessLayer
    private val controller = EditGoalsController(model)
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_goals)
        controller.bind(this)
        super.onCreate(savedInstanceState)
    }
    override fun onStart() {
        val data = model.getSettingsRepository().getSettings()
        findViewById<TextView>(R.id.currentWeight).text = data.currentBodyWeight.toString()
        findViewById<TextView>(R.id.aimWeight).text = data.aimBodyWeight.toString()
        //findViewById<Spinner>(R.id.weightChange).text = data.weightChange.toString()
        findViewById<TextView>(R.id.weightChangePerMonth).text = data.weightChangePerMonth.toString()
        //findViewById<TextView>(R.id.physicialActivity).text = data.physicalActivity
        findViewById<TextView>(R.id.waterPerDay).text = data.waterPerDay.toString()
        findViewById<TextView>(R.id.caloriesPerDay).text = data.caloriesPerDay.toString()
        findViewById<TextView>(R.id.carbohydratesPerDay).text = data.carbohydratesPerDay.toString()
        findViewById<TextView>(R.id.proteinPerDay).text = data.proteinPerDay.toString()
        findViewById<TextView>(R.id.fatPerDay).text = data.fatPerDay.toString()
        findViewById<ImageView>(R.id.backToSettings).setOnClickListener {
            finish()
        }

        super.onStart()

    }


    override fun onStop() {
        var data: SettingsEntity = model.getSettingsRepository().getSettings()
        data.currentBodyWeight = findViewById<TextView>(R.id.currentWeight).text.toString().toFloat()
        data.aimBodyWeight = findViewById<TextView>(R.id.aimWeight).text.toString().toFloat()
        //findViewById<Spinner>(R.id.weightChange).text = data.weightChange.toString()
       data.weightChangePerMonth =  findViewById<TextView>(R.id.weightChangePerMonth).text.toString().toFloat()
        //findViewById<TextView>(R.id.physicialActivity).text = data.physicalActivity
        data.waterPerDay = findViewById<TextView>(R.id.waterPerDay).text.toString().toFloat()
        data.caloriesPerDay = findViewById<TextView>(R.id.caloriesPerDay).text.toString().toInt()
        data.carbohydratesPerDay = findViewById<TextView>(R.id.carbohydratesPerDay).text.toString().toInt()
        data.proteinPerDay = findViewById<TextView>(R.id.proteinPerDay).text.toString().toInt()
        data.fatPerDay = findViewById<TextView>(R.id.fatPerDay).text.toString().toInt()
        controller.saveChangedGoals(data)
        finish()
        super.onStop()

    }
}