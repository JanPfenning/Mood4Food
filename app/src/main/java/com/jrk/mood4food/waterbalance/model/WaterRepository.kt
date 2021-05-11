package com.jrk.mood4food.waterbalance.model

import android.content.Context
import com.jrk.mood4food.App
import com.jrk.mood4food.model.localStorage.LocalStorage
import com.jrk.mood4food.model.localStorage.LocalStorageInterface
import java.text.SimpleDateFormat
import java.util.*

class WaterRepository(localStorage: LocalStorageInterface) {
    var localStorage = localStorage
    fun storeWaterBalance(waterBalance: Float) {
        val context: Context = App.getContext()
        val waterEntity = getEntityFromDate(convertDateToFormattedString(Calendar.getInstance().time))
        waterEntity.waterBalance += waterBalance
        LocalStorage.save(context, waterEntity)
    }

    fun createWaterEntity(currentDate: String, waterBalance: Float): WaterBalanceEntity {
        val context: Context = App.getContext()
        val waterEntity = WaterBalanceEntity(context)
        val cal: Calendar = getCalenderFromDate(currentDate)
        waterEntity.waterBalance = waterBalance
        waterEntity.currentDate = currentDate
        waterEntity.calenderWeek = getCalenderWeekFromDate(cal)
        waterEntity.dayOfWeek = getDayOfWeek(cal)
        localStorage.save(context, waterEntity)
        return waterEntity

    }

    private fun getCalenderFromDate(currentDate: String): Calendar {
        val format = "dd.MM.yyyy"

        val df = SimpleDateFormat(format)
        val date = df.parse(currentDate)

        val cal = Calendar.getInstance()
        cal.time = date
        return cal
    }

    private fun getDayOfWeek(cal: Calendar): Int {
        return cal[Calendar.DAY_OF_WEEK]
    }

    private fun getCalenderWeekFromDate(cal: Calendar): Int {
        return cal[Calendar.WEEK_OF_YEAR]
    }


    fun getCurrentWaterBalancePercentage(): Float {
        val entity = getEntityFromDate(convertDateToFormattedString(Calendar.getInstance().time))
        return (entity.waterBalance * 100 / getWaterLevel())

    }

    fun getCurrentWaterBalanceAbsolut(): Float {
        val entity = getEntityFromDate(convertDateToFormattedString(Calendar.getInstance().time))
        return (entity.waterBalance)

    }

    private fun convertDateToFormattedString(date: Date): String {
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        return formatter.format(date).toString()
        return formatter.format(date).toString()
    }

    //TODO REFCTOR
    fun getWaterEntityFromWeekOfYear(Calenderweek: Int): Pair<MutableList<WaterBalanceEntity>, MutableList<Boolean>> {
        val entities = localStorage.getAll(App.getContext(), WaterBalanceEntity::class.java) as List<WaterBalanceEntity>
        var entitiesFromWeek: MutableList<WaterBalanceEntity> = mutableListOf()
        var d = listOf(1, 2, 3, 4, 5, 6, 7).toMutableList()
        entities.forEach {
            if (it.calenderWeek == Calenderweek) {
                entitiesFromWeek.add(it)
                d.remove(it.dayOfWeek)
            }
        }
        for (i in d) {
            val waterEntity = WaterBalanceEntity(App.getContext())
            waterEntity.dayOfWeek = i
            waterEntity.waterBalance = 0F
            entitiesFromWeek.add(waterEntity)
        }
        var isReached: MutableList<Boolean> = arrayListOf()

        var ret = entitiesFromWeek.sortedBy { it.dayOfWeek }
        ret = ret.toMutableList()
        for (entity in entitiesFromWeek) {
            if (entity.waterBalance >= getWaterLevel()) {
                isReached.add(true)
            } else {
                isReached.add(false)
            }
        }

        return Pair(ret, isReached)

    }

    fun isWaterLevelReached(date: Date): Boolean {
        return getEntityFromDate(convertDateToFormattedString(date)).waterBalance >= getWaterLevel()

    }


    fun getEntityFromDate(date: String): WaterBalanceEntity {

        val entities = localStorage.getAll(App.getContext(), WaterBalanceEntity::class.java) as List<WaterBalanceEntity>


        entities.forEach {
            if (it.currentDate == date) {
                return it
            }
        }
        return createWaterEntity(date, 0.0F)

    }

    fun getWaterLevel(): Float {
        var entities = localStorage.getAll(App.getContext(), SettingsEntity::class.java) as List<SettingsEntity>
        return entities[0].waterPerDay
    }

    fun resetWaterbalance() {
        val waterEntity = getEntityFromDate(convertDateToFormattedString(Calendar.getInstance().time))
        waterEntity.waterBalance = 0F
        localStorage.save(App.getContext(), waterEntity)
    }


}