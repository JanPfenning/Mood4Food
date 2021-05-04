package com.jrk.mood4food.waterbalance.controller

import com.jrk.mood4food.model.DataAccessLayer
import com.jrk.mood4food.waterbalance.view.WaterAnalyserView

class WaterAnalyserController(private val model: DataAccessLayer) {
    private lateinit var view: WaterAnalyserView
    fun bind(waterView: WaterAnalyserView) {
        view = waterView

    }


}
