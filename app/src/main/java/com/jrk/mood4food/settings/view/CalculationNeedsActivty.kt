package com.jrk.mood4food.settings.view

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import com.jrk.mood4food.App
import com.jrk.mood4food.NavBarActivity
import com.jrk.mood4food.R
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.settings.Gender
import com.jrk.mood4food.settings.controller.GoalController
import com.jrk.mood4food.waterbalance.model.SettingsEntity
import com.jrk.mood4food.waterbalance.model.SettingsObserver


class CalculationNeedsActivty : NavBarActivity(), SettingsObserver, CalculationView {
    private val model = ModelModule.dataAccessLayer
    private val controller = GoalController(model)
    private lateinit var dataAdapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_calculation)
        controller.bind(this)
        findViewById<TextView>(R.id.calcResult).setOnClickListener {
            controller.calculateNeeds()

        }
        findViewById<ImageView>(R.id.backToSettings).setOnClickListener {
            finish()
        }

        val genderSpinnerItems: MutableList<String> = ArrayList()
        genderSpinnerItems.add(Gender.Female.name)
        genderSpinnerItems.add(Gender.Male.name)

        dataAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genderSpinnerItems)
        var spinner = findViewById<Spinner>(R.id.gender) as Spinner
        spinner.adapter = dataAdapter
        showCurrentSettings()

        super.onCreate(savedInstanceState)

    }

    override fun calculationOfNeedsDone() {

        startActivity(Intent(this, ResultsCalculationActivity::class.java))

    }

    override fun getCalculationData(): SettingsEntity {
        var settingsEntity = SettingsEntity(App.getContext())
        settingsEntity.currentBodyWeight = findViewById<TextView>(R.id.currentWeight).text.toString().toFloat()
        settingsEntity.aimBodyWeight = findViewById<TextView>(R.id.aimBodyWeight).text.toString().toFloat()
        settingsEntity.bodySize = findViewById<TextView>(R.id.bodySize).text.toString().toInt()
        settingsEntity.age = findViewById<TextView>(R.id.age).text.toString().toInt()
        settingsEntity.gender = findViewById<Spinner>(R.id.gender).selectedItem.toString()

        return settingsEntity
    }

    override fun showCurrentSettings() {
        var currentSettings = model.getSettingsRepository().getSettings()
        findViewById<TextView>(R.id.currentWeight).text = currentSettings.currentBodyWeight.toString()
        findViewById<TextView>(R.id.aimBodyWeight).text = currentSettings.aimBodyWeight.toString()
        findViewById<TextView>(R.id.bodySize).text = currentSettings.bodySize.toString()
        findViewById<TextView>(R.id.age).text = currentSettings.age.toString()
        findViewById<Spinner>(R.id.gender).setSelection(dataAdapter.getPosition(currentSettings.gender))
    }

    override fun onStart() {
        super.onStart()
        model.register(this)
    }


    override fun onStop() {
        super.onStop()
        model.unregister(this)
        finish()
    }
}