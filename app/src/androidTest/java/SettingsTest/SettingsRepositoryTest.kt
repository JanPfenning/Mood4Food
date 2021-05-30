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


}



