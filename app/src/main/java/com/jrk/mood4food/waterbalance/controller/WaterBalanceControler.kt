package com.jrk.mood4food.waterbalance.controller

import android.util.Log
import com.jrk.mood4food.waterbalance.model.DataAccessLayer
import com.jrk.mood4food.waterbalance.view.WaterBalanceView

class WaterBalanceControler(private val model: DataAccessLayer) {
    private  lateinit var view: WaterBalanceView
    fun bind(waterView: WaterBalanceView) {
        view = waterView
    }

    fun onWaterAdd(waterAdd: Float) {
        Log.i("test", waterAdd.toString())
        model.performWaterAdd(view.getWaterAdd())



    }


}