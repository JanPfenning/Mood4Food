package com.jrk.mood4food.settings.view

import android.os.Bundle
import android.widget.TextView
import com.jrk.mood4food.NavBarActivity
import com.jrk.mood4food.R
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.settings.controller.ResultController
import com.jrk.mood4food.waterbalance.model.SettingsObserver

class ResultsCalculationActivty: NavBarActivity(), SettingsObserver{
    private val model = ModelModule.dataAccessLayer
    private val controller = ResultController(model)
    override fun onCreate(savedInstanceState: Bundle?) {
        controller.bind(this)
        setContentView(R.layout.activity_calculation_result)
        var data = model.getSettingsRepository().getCalculatedNeeds()
        findViewById<TextView>(R.id.waterPerDay).text = data.waterPerDay.toString()
        findViewById<TextView>(R.id.caloriesPerDay).text = data.caloriesPerDay.toString()
        findViewById<TextView>(R.id.carbohydratesPerDay).text = data.carbohydratesPerDay.toString()
        findViewById<TextView>(R.id.proteinPerDay).text = data.proteinPerDay.toString()
        findViewById<TextView>(R.id.fatPerDay).text = data.fatPerDay.toString()
        findViewById<TextView>(R.id.saveCalcResults).setOnClickListener(){
            controller.saveCalculationResults()
        }
        super.onCreate(savedInstanceState)

    }
    override fun calculationOfNeedsDone() {}
    override fun onStart() {
        super.onStart()
        model.register(this)
    }


    override fun onStop() {
        super.onStop()
        model.unregister(this)
    }


}