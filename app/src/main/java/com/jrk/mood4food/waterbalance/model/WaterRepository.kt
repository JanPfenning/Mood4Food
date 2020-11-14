package com.jrk.mood4food.waterbalance.model

import android.content.Context
import android.util.Log
import com.jrk.mood4food.App
import com.jrk.mood4food.model.local.ExampleEntity
import com.jrk.mood4food.model.local.LocalStorage

class WaterRepository {
    public fun storeWaterBalance(waterBalance:Float){

        val context: Context = App.getContext()

        val newEntity = WaterBalanceEntity(context)
        newEntity.waterBalance = waterBalance
        newEntity.saveToLocalStorage(newEntity)



    }
    public fun getCurrentWaterBalance():Float{
        var progress:Float = (100/getWaterLevel())
        val entities = LocalStorage.getAll(App.getContext(), WaterBalanceEntity::class.java) as List<*> as List<WaterBalanceEntity>

        return  entities.get(0).waterBalance


    }
    public fun getWaterLevel():Float{
        return 3.0F
    }
}