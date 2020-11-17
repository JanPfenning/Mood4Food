package com.jrk.mood4food.waterbalance.model

import android.content.Context
import com.jrk.mood4food.model.localStorage.LocalEntity

class WaterBalanceEntity(context: Context) : LocalEntity(context, WaterBalanceEntity::class.java, true) {

    var waterBalance = 0f
    var currentDate = "--"






}