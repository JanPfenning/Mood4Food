package com.jrk.mood4food.model.localStorage

import android.content.Context

interface LocalStorageInterface {
    fun getAll(context: Context, entityClass: Class<*>): List<LocalEntity>
    fun load(context: Context, storageAddress: String?, entity: LocalEntity?)
    fun save(context: Context, entity: LocalEntity?)
    fun remove(context: Context, entity: LocalEntity?)

}
