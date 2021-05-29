import com.jrk.mood4food.App
import com.jrk.mood4food.waterbalance.model.SettingsEntity
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

    fun getDummySettingsEntity(age: Int, currentBodyWeight: Float): SettingsEntity {
        val entity: SettingsEntity = SettingsEntity(App.getContext())
        entity.age = age
        entity.currentBodyWeight = currentBodyWeight
        return entity
    }

    fun getDummySettingsEntity(caloriesPerDay: Int): SettingsEntity {
        val entity: SettingsEntity = SettingsEntity(App.getContext())
        entity.caloriesPerDay = caloriesPerDay
        return entity
    }

    fun getDummySettingsEntity(currentBodyWeight: Float, bodySize: Int, gender: String, physicalActivity: String, age: Int): SettingsEntity {
        val entity: SettingsEntity = SettingsEntity(App.getContext())
        entity.currentBodyWeight = currentBodyWeight
        entity.bodySize = bodySize
        entity.gender = gender
        entity.physicalActivity = physicalActivity.toString()
        entity.age = age

        return entity
    }


}