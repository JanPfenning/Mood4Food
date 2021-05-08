package waterbalanceTest

import android.content.Context
import com.jrk.mood4food.model.localStorage.LocalEntity
import com.jrk.mood4food.model.localStorage.LocalStorageInterface

object TestStorage : LocalStorageInterface {
    private var list: MutableList<LocalEntity> = emptyArray<LocalEntity>().toMutableList()
    override fun getAll(context: Context, entityClass: Class<*>): List<LocalEntity> {
        return list
    }

    override fun load(context: Context, storageAddress: String?, entity: LocalEntity?) {
        TODO("Not yet implemented")
    }

    override fun save(context: Context, entity: LocalEntity?) {
        if (entity != null) {
            list.add(entity)
        }
    }

    override fun remove(context: Context, entity: LocalEntity?) {
        list.remove(entity)
    }

    fun reset() {
        list = emptyArray<LocalEntity>().toMutableList()
    }
}