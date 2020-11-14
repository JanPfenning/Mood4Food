package com.jrk.mood4food.model.local

import android.content.Context
import android.util.Log
import java.io.File
import java.lang.reflect.Field
import java.util.*

open class LocalEntity(private val context: Context, entity: Class<*>, hasEntitySet: Boolean) {
    var validDataTypes = arrayOf(
            "class java.lang.String",
            "int",
            "boolean",
            "long",
            "float",
            "interface java.util.Set"
    )
    var storageAddress: String? = null
        private set
    val entityName: String
    private val hasEntitySet: Boolean
    private val attributes: MutableList<Field> = ArrayList()

    fun hasEntitySet(): Boolean {
        return hasEntitySet
    }

    fun loadFromLocalStorage(storageAddress: String?, entity: LocalEntity?) {
        this.storageAddress = storageAddress
        val entityFile = context.getSharedPreferences(storageAddress, 0)
        for (attribute in attributes) {
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

    fun saveToLocalStorage(entity: LocalEntity?) {
        if (storageAddress == null) {
            if (hasEntitySet) {
                val entitySetFile = context.getSharedPreferences(entityName, 0)
                val entityNum = entitySetFile.getInt("$entityName.length", 0)
                storageAddress = "$entityName#$entityNum"
                entitySetFile.edit().putInt("$entityName.length", entityNum + 1).putInt(storageAddress, entityNum).apply()
            } else {
                storageAddress = entityName
            }
        }
        val entityFile = context.getSharedPreferences(entityName, 0)
        val editor = entityFile.edit()
        for (attribute in attributes) {
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

    fun removeFromLocalStorage() {
        if (storageAddress != null) {
            val entityFile = context.getSharedPreferences(storageAddress, 0)
            entityFile.edit().clear().apply()
            val file = File(context.filesDir.parent + "/shared_prefs/" + storageAddress)
            file.delete()
            if (hasEntitySet) {
                val entitySetFile = context.getSharedPreferences(entityName, 0)
                entityFile.edit().remove(storageAddress).apply()
            }
        }
    }

    init {
        entityName = entity.name
        this.hasEntitySet = hasEntitySet
        for (attribute in entity.declaredFields) {
            var isValidAttribute = true
            if (!Arrays.asList(*validDataTypes).contains(attribute.type.toString())) {
                for (annotation in attribute.annotations) {
                    if (annotation.annotationClass == IgnoreInStorage::class.java) {
                        isValidAttribute = false
                    }
                }
                if(!isValidAttribute) {
                    Log.e("LocalStorage", "The class '" + entityName + "' has attributes of data types which can not be stored in SharedPreferences. Valid data types are: " + Arrays.toString(validDataTypes))
                }else{
                    isValidAttribute = false;
                }
            }
            if (isValidAttribute) {
                attribute.isAccessible = true;
                attributes.add(attribute)
            }
        }
    }
}