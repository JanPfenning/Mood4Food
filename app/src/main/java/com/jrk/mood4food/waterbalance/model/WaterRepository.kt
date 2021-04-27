package com.jrk.mood4food.waterbalance.model

import android.content.Context
import com.jrk.mood4food.App
import com.jrk.mood4food.model.localStorage.LocalStorage
import java.text.SimpleDateFormat
import java.util.*

class WaterRepository {
    fun storeWaterBalance(waterBalance: Float) {
        val context: Context = App.getContext()
        val waterEntity = getEntityFromDate(Calendar.getInstance().time)
        waterEntity.waterBalance += waterBalance
        LocalStorage.save(context, waterEntity)
    }

    private fun createWaterEntity(currentDate: String, waterBalance: Float): WaterBalanceEntity {
        val context: Context = App.getContext()
        val waterEntity = WaterBalanceEntity(context)
        waterEntity.waterBalance = waterBalance
        waterEntity.currentDate = currentDate
        LocalStorage.save(context, waterEntity)
        return waterEntity

    }

    fun getCurrentWaterBalancePercentage(): Float {
        val entity = getEntityFromDate(Calendar.getInstance().time)
        return (entity.waterBalance * 100 / getWaterLevel())

    }

    fun getCurrentWaterBalanceAbsolut(): Float {
        val entity = getEntityFromDate(Calendar.getInstance().time)
        return (entity.waterBalance)

    }

    fun isWaterLevelReached(): Boolean {
        return getEntityFromDate(Calendar.getInstance().time).waterBalance >= getWaterLevel()

    }

    private fun getEntityFromDate(date: Date): WaterBalanceEntity {
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        val entities = LocalStorage.getAll(App.getContext(), WaterBalanceEntity::class.java) as List<WaterBalanceEntity>
        val dateFormatted = formatter.format(date)

        entities.forEach {
            if (it.currentDate == dateFormatted.toString()) {
                return it
            }
        }
        return createWaterEntity(dateFormatted, 0.0F)

    }

    fun getWaterLevel(): Float {
        var entities = LocalStorage.getAll(App.getContext(), SettingsEntity::class.java) as List<SettingsEntity>
        return entities[0].waterPerDay
    }

    fun resetWaterbalance() {
        val waterEntity = getEntityFromDate(Calendar.getInstance().time)
        waterEntity.waterBalance = 0F
        LocalStorage.save(App.getContext(), waterEntity)
    }


}