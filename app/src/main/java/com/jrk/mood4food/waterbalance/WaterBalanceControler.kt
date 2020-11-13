package com.jrk.mood4food.waterbalance

import android.util.Log

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