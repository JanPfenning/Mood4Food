package SettingsTest


import DummyData
import TestStorage
import com.jrk.mood4food.App
import com.jrk.mood4food.waterbalance.model.SettingsRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SettingsRepositoryTest {
    private lateinit var settingsRepository: SettingsRepository


    @Before
    fun resetAll() {
        settingsRepository = SettingsRepository(TestStorage)
        TestStorage.reset()

    }

    @Test
    fun getSettingsTest() {
        val entityMale = DummyData.getDummySettingsEntity(80F, 187, "Male", "Extreme Belastung", 18)
        TestStorage.save(App.getContext(), entityMale)
        Assert.assertEquals(entityMale, settingsRepository.getSettings())
    }

    @Test
    fun firstStartTest() {
        settingsRepository.firstStart()
        Assert.assertEquals(2.0F, settingsRepository.getSettings().waterPerDay)
    }

    @Test
    fun calculateChangedGoalsTestIfCaloriesChanged() {
        var entityMale = DummyData.getDummySettingsEntity(80F, 187, "Male", "Extreme Belastung", 18)
        settingsRepository.calculateNeeds(entityMale)
        TestStorage.save(App.getContext(), settingsRepository.getTempEntity())
        var c = DummyData.getDummySettingsEntity(5000)
        settingsRepository.calculateChangedGoals(c)
        Assert.assertEquals(670, settingsRepository.getSettings().carbohydratesPerDay)
        Assert.assertEquals(161, settingsRepository.getSettings().fatPerDay)
        Assert.assertEquals(182, settingsRepository.getSettings().proteinPerDay)


    }


}



