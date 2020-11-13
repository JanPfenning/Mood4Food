package com.jrk.mood4food.model.local

import android.content.Context
import java.util.*

object LocalStorage {
    fun getAll(context: Context, entityClass: Class<*> /*, filter */): List<LocalEntity> {
        val entities: MutableList<LocalEntity> = ArrayList()
        try {
            val entity = entityClass.newInstance() as LocalEntity
            if (entity.hasEntitySet()) {
                val entitySetFile = context.getSharedPreferences(entityClass.name, 0)
                for (key in entitySetFile.all.keys) {
                    entity.loadFromLocalStorage(key, entity)
                    entities.add(entity)
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