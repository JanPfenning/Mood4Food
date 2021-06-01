package SettingsTest


import DummyData
import com.jrk.mood4food.settings.model.NeedsCalculator
import org.junit.Assert
import org.junit.Test

class NeedsCalculatorTest {


    @Test
    fun calcCarbohydratePerDayTest() {
        val entity = DummyData.getDummySettingsEntity(500)
        val carbohydratePerDay = NeedsCalculator.calcCarbohydratePerDay(entity)
        Assert.assertEquals(67, carbohydratePerDay)

    }

    @Test
    fun calcProteinPerDayTest() {
        val entity = DummyData.getDummySettingsEntity(500)
        val proteinPerDay = NeedsCalculator.calcProteinPerDay(entity)
        Assert.assertEquals(18, proteinPerDay)

    }

    @Test
    fun calcFatPerDayTest() {
        val entity = DummyData.getDummySettingsEntity(500)
        val fatPerDay = NeedsCalculator.calcFatPerDay(entity)
        Assert.assertEquals(16, fatPerDay)

    }

    @Test
    fun calcWaterPerDayTest() {
        val entity = DummyData.getDummySettingsEntity(18, 80.0F)
        val waterPerDay = NeedsCalculator.calcWaterPerDay(entity)
        Assert.assertEquals(1.5F, waterPerDay)

    }

    @Test
    fun calcCaloriesPerDayTest() {
        val entityMale = DummyData.getDummySettingsEntity(80F, 187, "Male", "Extreme Belastung", 18)
        val caloriesPerDayMale = NeedsCalculator.calcCaloriesPerDay(entityMale)
        Assert.assertEquals(3625, caloriesPerDayMale)
        val entityFemale = DummyData.getDummySettingsEntity(80F, 187, "Female", "Extreme Belastung", 18)
        val caloriesPerDayFemale = NeedsCalculator.calcCaloriesPerDay(entityFemale)
        Assert.assertEquals(2815, caloriesPerDayFemale)


    }


}



