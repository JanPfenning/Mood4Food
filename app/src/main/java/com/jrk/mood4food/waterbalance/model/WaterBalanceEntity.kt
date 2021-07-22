package com.jrk.mood4food.waterbalance.model

import android.content.Context
import com.jrk.mood4food.model.localStorage.LocalEntity

class WaterBalanceEntity(context: Context) : LocalEntity(context, WaterBalanceEntity::class.java, true) {

    var waterBalance = 0f
    var currentDate = "--"
    var calenderWeek = 0
    var dayOfWeek = 0
    var isReached = false


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WaterBalanceEntity

        if (waterBalance != other.waterBalance) return false
        if (currentDate != other.currentDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = waterBalance.hashCode()
        result = 31 * result + currentDate.hashCode()
        return result
    }


}
