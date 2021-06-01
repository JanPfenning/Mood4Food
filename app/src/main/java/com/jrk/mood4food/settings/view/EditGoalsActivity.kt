package com.jrk.mood4food.settings.view

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import com.jrk.mood4food.NavBarActivity
import com.jrk.mood4food.R
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.settings.PhysicalActivity
import com.jrk.mood4food.settings.controller.EditGoalsController
import com.jrk.mood4food.waterbalance.model.SettingsEntity

class EditGoalsActivity : NavBarActivity() {
    private val model = ModelModule.dataAccessLayer
    private val controller = EditGoalsController(model)
    private lateinit var adapterPhysicialActivity: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_goals)
        controller.bind(this)
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        val physicalActivitySpinnerItems: MutableList<String> = ArrayList()
        physicalActivitySpinnerItems.add(PhysicalActivity.Lowest.text)
        physicalActivitySpinnerItems.add(PhysicalActivity.Low.text)
        physicalActivitySpinnerItems.add(PhysicalActivity.Middle.text)
        physicalActivitySpinnerItems.add(PhysicalActivity.High.text)
        physicalActivitySpinnerItems.add(PhysicalActivity.Highest.text)

        adapterPhysicialActivity = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, physicalActivitySpinnerItems)
        var spinner = findViewById<Spinner>(R.id.physicialActivity2) as Spinner
        spinner.adapter = adapterPhysicialActivity

        displayCurrentSettings()
        findViewById<ImageView>(R.id.backToSettings).setOnClickListener {
            finish()
        }

        super.onStart()

    }

    private fun displayCurrentSettings() {
        val currentSettings = model.getSettingsRepository().getSettings()
        findViewById<TextView>(R.id.currentWeight).text = currentSettings.currentBodyWeight.toString()
        findViewById<TextView>(R.id.waterPerDay).text = currentSettings.waterPerDay.toString()
        findViewById<TextView>(R.id.caloriesPerDay).text = currentSettings.caloriesPerDay.toString()
        findViewById<TextView>(R.id.carbohydratesPerDay).text = currentSettings.carbohydratesPerDay.toString()
        findViewById<TextView>(R.id.proteinPerDay).text = currentSettings.proteinPerDay.toString()
        findViewById<TextView>(R.id.fatPerDay).text = currentSettings.fatPerDay.toString()
        findViewById<Spinner>(R.id.physicialActivity2).setSelection(adapterPhysicialActivity.getPosition(currentSettings.physicalActivity))
    }


    override fun onStop() {
        var data: SettingsEntity = model.getSettingsRepository().getSettings()
        data.currentBodyWeight = findViewById<TextView>(R.id.currentWeight).text.toString().toFloat()
        data.physicalActivity = findViewById<Spinner>(R.id.physicialActivity2).selectedItem.toString()
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