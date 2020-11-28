package com.jrk.mood4food.settings.view

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
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
    override fun onStart() {
        super.onStart()
        model.register(this)
    }


    override fun onStop() {
        super.onStop()
        model.unregister(this)
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

    override fun calculationOfNeedsDone() {
        var data = model.getSettingsRepository().getCalculatedNeeds()
        Log.i("Test", data.waterPerDay.toString() + "hier2" )
        findViewById<TextView>(R.id.waterPerDay).text = data.waterPerDay.toString()

    }
}