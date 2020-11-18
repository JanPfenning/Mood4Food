package com.jrk.mood4food.model

import com.jrk.mood4food.waterbalance.model.WaterRepository

object ModelModule {
    val dataAccessLayer: DataAccessLayer by lazy { dataAccessLayer() }
    private fun dataAccessLayer() = DataAccessLayer(WaterRepository())



}