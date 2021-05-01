package com.jrk.mood4food.waterbalance.model

import android.content.Context
import android.util.Log
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
        val cal: Calendar = getcalenderfromDate(currentDate)
        waterEntity.waterBalance = waterBalance
        waterEntity.currentDate = currentDate
        waterEntity.calenderWeek = getCalenderWeekfromDate(currentDate, cal)
        waterEntity.dayOfWeek = getDayofWeek(currentDate, cal)
        Log.i("Test", waterEntity.dayOfWeek.toString())
        LocalStorage.save(context, waterEntity)
        return waterEntity

    }

    private fun getcalenderfromDate(currentDate: String): Calendar {
        val format = "dd.MM.yyyy"

        val df = SimpleDateFormat(format)
        val date = df.parse(currentDate)

        val cal = Calendar.getInstance()
        cal.time = date
        return cal
    }

    private fun getDayofWeek(currentDate: String, cal: Calendar): Int {
        return cal[Calendar.DAY_OF_WEEK] - 2
    }

    private fun getCalenderWeekfromDate(currentDate: String, cal: Calendar): Int {
        return cal[Calendar.WEEK_OF_YEAR]
    }


    fun getCurrentWaterBalancePercentage(): Float {
        val entity = getEntityFromDate(Calendar.getInstance().time)
        return (entity.waterBalance * 100 / getWaterLevel())

    }

    fun getCurrentWaterBalanceAbsolut(): Float {
        val entity = getEntityFromDate(Calendar.getInstance().time)
        return (entity.waterBalance)

    }

    //TODO REFCTOR
    fun getWaterEntityFromWeekofYear(Calenderweek: Int): Pair<MutableList<WaterBalanceEntity>, MutableList<Boolean>> {
        val entities = LocalStorage.getAll(App.getContext(), WaterBalanceEntity::class.java) as List<WaterBalanceEntity>
        val entitiesFromWeek: MutableList<WaterBalanceEntity> = mutableListOf()
        entities.forEach {
            if (it.calenderWeek == Calenderweek) {
                entitiesFromWeek.add(it)
            }
        }
        entitiesFromWeek.sortedBy { it.dayOfWeek }
        var d = listOf(0, 1, 2, 3, 4, 5, 6).toMutableList()
        for (entity in entitiesFromWeek) {
            d.remove(entity.dayOfWeek)

        }
        for (i in d) {
            val waterEntity = WaterBalanceEntity(App.getContext())
            waterEntity.dayOfWeek = i
            waterEntity.waterBalance = 0F
            entitiesFromWeek.add(waterEntity)

        }
        var isreached: MutableList<Boolean> = arrayListOf()
        entitiesFromWeek.sortedBy { it.dayOfWeek }
        for (entitiy in entitiesFromWeek) {
            if (entitiy.waterBalance >= getWaterLevel()) {
                isreached.add(true)
            } else {
                isreached.add(false)
            }
        }
        return Pair(entitiesFromWeek, isreached)

    }

    fun isWaterLevelReached(date: Date): Boolean {
        return getEntityFromDate(date).waterBalance >= getWaterLevel()

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