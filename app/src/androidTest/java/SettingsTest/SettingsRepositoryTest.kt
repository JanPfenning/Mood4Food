package SettingsTest


import TestStorage
import com.jrk.mood4food.waterbalance.model.SettingsRepository
import org.junit.Before

class SettingsRepositoryTest {
    private lateinit var settingsRepository: SettingsRepository


    @Before
    fun resetAll() {
        settingsRepository = SettingsRepository(TestStorage)

        TestStorage.reset()

    }


}



