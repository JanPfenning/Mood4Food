package com.jrk.mood4food.waterbalance.controller

import com.jrk.mood4food.model.DataAccessLayer
import com.jrk.mood4food.waterbalance.view.SettingsView
import com.jrk.mood4food.waterbalance.view.WaterBalanceActivity
import com.jrk.mood4food.waterbalance.view.WaterBalanceView

class WaterBalanceControler(private val model: DataAccessLayer) {
    private  lateinit var view: WaterBalanceView
    fun bind(waterView: WaterBalanceActivity) {
        view = waterView

    }

    fun onWaterAdd(waterAdd: Float) {
        model.performWaterAdd(view.getWaterAdd())
    }


}