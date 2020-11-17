package com.jrk.mood4food.waterbalance.model

import android.content.Context
import com.jrk.mood4food.App
import com.jrk.mood4food.model.localStorage.LocalStorage
import java.text.SimpleDateFormat

class WaterRepository {
    public fun storeWaterBalance(waterBalance: Float) {
        val date = java.util.Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        val currentDate = formatter.format(date)
        var entities = LocalStorage.getAll(App.getContext(), WaterBalanceEntity::class.java) as List<*> as List<WaterBalanceEntity>
        var exist = false

        entities.forEach {
            if (it.currentDate == currentDate.toString()) {
                exist = true
                it.waterBalance += waterBalance
            }
        }
        if (!exist) {
            newWaterEntity(currentDate, waterBalance)
        }
    }

        private fun newWaterEntity(currentDate: String, waterBalance: Float) {
            val context: Context = App.getContext()
            val newEntity = WaterBalanceEntity(context)
            newEntity.waterBalance = waterBalance
            newEntity.currentDate = currentDate
            newEntity.saveToLocalStorage(newEntity)

        }

        public fun getCurrentWaterBalance(): Float {
            val date = java.util.Calendar.getInstance().time
            val formatter = SimpleDateFormat("dd.MM.yyyy")
            val currentDate = formatter.format(date)
            val entities = LocalStorage.getAll(App.getContext(), WaterBalanceEntity::class.java) as List<*> as List<WaterBalanceEntity>
            var currentBalance = 0.0F
            entities.forEach {

                if (it.currentDate == currentDate) {
                    currentBalance = it.waterBalance
                }
            }

            return (100 / getWaterLevel()) * currentBalance

        }

        private fun getWaterLevel(): Float {
            return 3.0F
        }



}