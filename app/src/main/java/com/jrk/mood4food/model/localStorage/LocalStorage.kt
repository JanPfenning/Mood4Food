package com.jrk.mood4food.model.localStorage

import android.content.Context
import java.util.*

object LocalStorage {
    fun getAll(context: Context, entityClass: Class<*>): List<LocalEntity> {
        val entities: MutableList<LocalEntity> = ArrayList()
        try {
            val entity = entityClass.getDeclaredConstructor(Context::class.java).newInstance(context) as LocalEntity
            if (entity.hasEntitySet()) {
                val entitySetFile = context.getSharedPreferences(entityClass.name, 0)
                for (key in entitySetFile.all.keys) {
                    if(key.contains("#")) {

                        val entry = entityClass.getDeclaredConstructor(Context::class.java).newInstance(context) as LocalEntity
                        entry.loadFromLocalStorage(key, entry)
                        entities.add(entry)
                    }
                }
            } else {
                entity.loadFromLocalStorage(entityClass.name, entity)
                entities.add(entity)
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        }
        return entities
    }
}