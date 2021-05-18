package waterbalanceTest


import com.jrk.mood4food.App
import com.jrk.mood4food.waterbalance.model.SettingsEntity
import com.jrk.mood4food.waterbalance.model.SettingsRepository
import com.jrk.mood4food.waterbalance.model.WaterBalanceEntity
import com.jrk.mood4food.waterbalance.model.WaterRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class WaterBalanceRepositoryTest {
    private lateinit var waterRepository: WaterRepository


    @Before
    fun resetAll() {
        waterRepository = WaterRepository(TestStorage)

        TestStorage.reset()

    }

    private fun convertDateToFormattedString(date: Date): String {
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        return formatter.format(date).toString()
        return formatter.format(date).toString()
    }

    @Test
    fun createWaterEntityTest() {
        val entity = DummyData.getDummyWaterEntity(4.0F, "01.01.2021", 53, 6)
        Assert.assertEquals(entity, waterRepository.createWaterEntity("01.01.2021", 4.0F))
        Assert.assertEquals(1, TestStorage.getAll(App.getContext(), WaterBalanceEntity::class.java).size)
    }

    @Test
    fun getEntityFromDateIfNoEntityExistTest() {
        waterRepository.getEntityFromDate(convertDateToFormattedString(Calendar.getInstance().time))
        val e = TestStorage.getAll(App.getContext(), WaterBalanceEntity::class.java) as List<WaterBalanceEntity>
        val date = SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().time)
        Assert.assertEquals(1, TestStorage.getAll(App.getContext(), WaterBalanceEntity::class.java).size)
        Assert.assertEquals(date, e[0].currentDate)

    }

    @Test
    fun getEntityFromDateTest() {
        val entity1 = DummyData.getDummyWaterEntity(3.0F, "22.04.2000", 4, 4)
        val entity3 = DummyData.getDummyWaterEntity(3.0F, "22.06.2000", 4, 4)
        TestStorage.save(App.getContext(), entity1)
        TestStorage.save(App.getContext(), entity3)

        Assert.assertEquals(entity1, waterRepository.getEntityFromDate("22.04.2000"))
        Assert.assertEquals(entity3, waterRepository.getEntityFromDate("22.06.2000"))
        Assert.assertNotEquals(entity3, waterRepository.getEntityFromDate("22.09.2000"))

    }

    @Test
    fun storeWaterBalanceTest() {
        waterRepository.getEntityFromDate(convertDateToFormattedString(Calendar.getInstance().time))
        waterRepository.storeWaterBalance(20F)
        val e = TestStorage.getAll(App.getContext(), WaterBalanceEntity::class.java) as List<WaterBalanceEntity>
        var waterBalance = e[0].waterBalance
        Assert.assertEquals(20F, waterBalance)
        waterRepository.storeWaterBalance(20F)
        waterBalance = e[0].waterBalance
        Assert.assertEquals(40F, waterBalance)
    }

    @Test
    fun resetWaterbalanceTest() {
        waterRepository.getEntityFromDate(convertDateToFormattedString(Calendar.getInstance().time))
        waterRepository.storeWaterBalance(20F)
        waterRepository.resetWaterbalance()
        val e = TestStorage.getAll(App.getContext(), WaterBalanceEntity::class.java) as List<WaterBalanceEntity>
        var waterBalance = e[0].waterBalance
        Assert.assertEquals(0F, waterBalance)
    }

    /* TODO
    @Test
    fun getWaterEntityFromWeekOfYearTest() {
        val settingsRepository = SettingsRepository(TestStorage)
        val settingsEntity = SettingsEntity(App.getContext());
        settingsEntity.waterPerDay = 4.0F
        settingsRepository.storeSettings(settingsEntity)
        val entity1 = DummyData.getDummyWaterEntity(20F, "22.04.2000", 3, 1)
        val entity2 = DummyData.getDummyWaterEntity(20F, "23.04.2000", 3, 3)
        val entity3 = DummyData.getDummyWaterEntity(20F, "24.04.2000", 3, 5)
        val entity4 = DummyData.getDummyWaterEntity(20F, "25.04.2000", 5, 4)
        TestStorage.save(App.getContext(), entity1)
        TestStorage.save(App.getContext(), entity2)
        TestStorage.save(App.getContext(), entity3)
        TestStorage.save(App.getContext(), entity4)
        val ret = waterRepository.getWaterEntityFromWeekOfYear(3)
        val entities = ret.first
        Assert.assertEquals("22.04.2000", entities[0].currentDate)
        Assert.assertEquals("23.04.2000", entities[2].currentDate)
        Assert.assertEquals("24.04.2000", entities[4].currentDate)
    }
    */

    @Test
    fun convertDateToFormattedString() {
        val dateString = convertDateToFormattedString(Date(100, 3, 22))
        Assert.assertEquals("22.04.2000", dateString)

    }

    @Test
    fun isWaterLevelReachedTest() {
        val settingsRepository = SettingsRepository(TestStorage)
        val settingsEntity = SettingsEntity(App.getContext());
        settingsEntity.waterPerDay = 4.0F
        settingsRepository.storeSettings(settingsEntity)
        val entity1 = DummyData.getDummyWaterEntity(5F, "22.04.2000", 3, 1)
        val entity2 = DummyData.getDummyWaterEntity(1F, "23.04.2000", 3, 1)
        TestStorage.save(App.getContext(), entity1)
        TestStorage.save(App.getContext(), entity2)
        val e = waterRepository.getEntityFromDate("22.04.2000")
        Assert.assertFalse(waterRepository.isWaterLevelReached(Date(100, 3, 23)))
        Assert.assertTrue(waterRepository.isWaterLevelReached(Date(100, 3, 22)))


    }

}



