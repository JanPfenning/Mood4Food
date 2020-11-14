package com.jrk.mood4food.waterbalance.model

import android.content.Context
import android.util.Log
import com.jrk.mood4food.App
import com.jrk.mood4food.model.local.ExampleEntity

class WaterRepository {
    public fun storeWaterBalance(waterBalance:Float){
        var progress:Float = (100/getWaterLevel())*waterBalance
        val context: Context = App.getContext()

        val newEntity = WaterBalanceEntity(context)
        newEntity.exampleFloat = waterBalance
        newEntity.saveToLocalStorage(newEntity)


    }
    public fun getCurrentWaterBalance():Float{
        val existingEntity = WaterBalanceEntity(App.getContext())

        existingEntity.loadFromLocalStorage("WaterBalance#1", existingEntity)
        return  existingEntity.exampleFloat


    }
    public fun getWaterLevel():Float{
        return 3.0F
    }
}