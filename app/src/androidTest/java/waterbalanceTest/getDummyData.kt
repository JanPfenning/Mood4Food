package waterbalanceTest

import com.jrk.mood4food.App
import com.jrk.mood4food.waterbalance.model.WaterBalanceEntity

object DummyData {
    fun getDummyWaterEntity(waterBalance: Float, date: String, calenderweek: Int, dayOfWeek: Int): WaterBalanceEntity {
        val entity: WaterBalanceEntity = WaterBalanceEntity(App.getContext())
        entity.waterBalance = waterBalance
        entity.currentDate = date
        entity.calenderWeek = calenderweek
        entity.dayOfWeek = dayOfWeek
        return entity
    }

}