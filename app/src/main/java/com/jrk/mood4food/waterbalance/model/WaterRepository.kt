package com.jrk.mood4food.waterbalance.model

import android.content.Context
import com.jrk.mood4food.App
import com.jrk.mood4food.model.localStorage.LocalStorage
import java.text.SimpleDateFormat
import java.util.*

class WaterRepository {
    public fun storeWaterBalance(waterBalance: Float) {
        val  entity = getEntityFromDate(java.util.Calendar.getInstance().time)
        entity.waterBalance += waterBalance
        entity.saveToLocalStorage(entity)
    }

        private fun createWaterEntity(currentDate: String, waterBalance: Float): WaterBalanceEntity {
            val context: Context = App.getContext()
            val newEntity = WaterBalanceEntity(context)
            newEntity.waterBalance = waterBalance
            newEntity.currentDate = currentDate
            newEntity.saveToLocalStorage(newEntity)
            return newEntity

        }

    public fun getCurrentWaterBalance(): Float {
            val  entity = getEntityFromDate(java.util.Calendar.getInstance().time)

            //Log.i("RICO","${entity.waterBalance} of ${getWaterLevel()} (${entity.waterBalance * 100/ getWaterLevel()}) is drunk at the ${entity.currentDate}");
            return (entity.waterBalance *100 / getWaterLevel())
        }
    public fun isWaterLevelReached():Boolean{
            return getEntityFromDate(java.util.Calendar.getInstance().time).waterBalance >= getWaterLevel()

        }
    public fun getEntityFromDate(date:Date): WaterBalanceEntity {
            val formatter = SimpleDateFormat("dd.MM.yyyy")
            val entities = LocalStorage.getAll(App.getContext(), WaterBalanceEntity::class.java) as List<*> as List<WaterBalanceEntity>
            val dateFormatted = formatter.format(date)

            entities.forEach {
                if (it.currentDate == dateFormatted.toString()) {
                    return  it
                }
            }
            return createWaterEntity(dateFormatted, 0.0F)

        }

    public fun getWaterLevel(): Float {
            var entities = LocalStorage.getAll(App.getContext(), SettingsEntity::class.java) as List<SettingsEntity>
            return entities[0].waterPerDay
        }



}