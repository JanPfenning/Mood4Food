package com.jrk.mood4food.model.localStorage

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
    val entityName: String
    private val hasEntitySet: Boolean
    val attributes: MutableList<Field> = ArrayList()

    fun hasEntitySet(): Boolean {
        return hasEntitySet
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