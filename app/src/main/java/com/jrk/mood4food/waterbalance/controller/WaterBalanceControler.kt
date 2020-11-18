package com.jrk.mood4food.waterbalance.controller

import com.jrk.mood4food.model.DataAccessLayer
import com.jrk.mood4food.waterbalance.view.WaterBalanceView

class WaterBalanceControler(private val model: DataAccessLayer) {
    private  lateinit var view: WaterBalanceView
    fun bind(waterView: WaterBalanceView) {
        view = waterView
    }

    fun onWaterAdd(waterAdd: Float) {
        model.performWaterAdd(view.getWaterAdd())
    }


}