package com.jrk.mood4food.model.localStorage

import android.content.Context
import java.io.File
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
                        load(context, key, entry)
                        entities.add(entry)
                    }
                }
            } else {
                load(context, entityClass.name, entity)
                entities.add(entity)
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        }
        return entities
    }

    fun load(context: Context, storageAddress: String?, entity: LocalEntity?) {
        entity?.storageAddress = storageAddress
        val entityFile = context.getSharedPreferences(storageAddress, 0)
        for (attribute in entity?.attributes!!) {
            try {
                when (attribute.type.toString()) {
                    "class java.lang.String" -> attribute[entity] = entityFile.getString(attribute.name, null)
                    "int" -> attribute.setInt(entity, entityFile.getInt(attribute.name, 0))
                    "boolean" -> attribute.setBoolean(entity, entityFile.getBoolean(attribute.name, false))
                    "long" -> attribute.setLong(entity, entityFile.getLong(attribute.name, 0))
                    "float" -> attribute.setFloat(entity, entityFile.getFloat(attribute.name, 0f))
                    "interface java.util.Set" -> attribute[entity] = entityFile.getStringSet(attribute.name, null)
                }
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
    }

    fun save(context: Context, entity: LocalEntity?) {
        if (entity?.storageAddress == null) {
            if (entity?.hasEntitySet()!!) {
                val entitySetFile = context.getSharedPreferences(entity.entityName, 0)
                val entityNum = entitySetFile.getInt("${entity.entityName}.length", 0)
                entity.storageAddress = "${entity.entityName}#$entityNum"
                entitySetFile.edit().putInt("${entity.entityName}.length", entityNum + 1).putInt(entity.storageAddress, entityNum).apply()
            } else {
                entity.storageAddress = entity.entityName
            }
        }
        val entityFile = context.getSharedPreferences(entity.storageAddress, 0)
        val editor = entityFile.edit()
        for (attribute in entity.attributes) {
            try {
                when (attribute.type.toString()) {
                    "class java.lang.String" -> {
                        if(attribute[entity] != null) {
                            editor.putString(attribute.name, attribute[entity] as String)
                        }
                    }
                    "int" -> editor.putInt(attribute.name, attribute[entity] as Int)
                    "boolean" -> editor.putBoolean(attribute.name, attribute[entity] as Boolean)
                    "long" -> editor.putLong(attribute.name, attribute[entity] as Long)
                    "float" -> editor.putFloat(attribute.name, attribute[entity] as Float)
                    "interface java.util.Set" -> {
                        if(attribute[entity] != null) {
                            editor.putStringSet(attribute.name, attribute[entity] as Set<String?>)
                        }
                    }
                }
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
        editor.apply()
    }

    fun remove(context: Context, entity: LocalEntity?) {
        if (entity?.storageAddress != null) {
            val entityFile = context.getSharedPreferences(entity.storageAddress, 0)
            entityFile.edit().clear().commit()
            val file = File(context.filesDir.parent + "/shared_prefs/" + entity.storageAddress + ".xml")
            file.delete()
            if (entity.hasEntitySet()) {
                val entitySetFile = context.getSharedPreferences(entity.entityName, 0)
                entitySetFile.edit().remove(entity.storageAddress).apply()
            }
        }
    }

}