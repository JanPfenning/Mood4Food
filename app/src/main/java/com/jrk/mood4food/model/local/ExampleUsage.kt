package com.jrk.mood4food.model.local

import android.content.Context

class ExampleUsage {
    init {

        // #1 Create new Entity & save to Local Storage
        val context: Context? = null // Application Context
        val newEntity = ExampleEntity(context!!)
        newEntity.exampleString = "Test"
        // Save attribute values
        newEntity.saveToLocalStorage(newEntity)

        // #2 Load Entity from Storage and update its values
        val existingEntity = ExampleEntity(context!!)
        existingEntity.loadFromLocalStorage("ExampleEntity#1", existingEntity)
        existingEntity.isExampleBoolean = true
        existingEntity.saveToLocalStorage(existingEntity)

        // #3 Delete Entity from LocalStorage
        val deleteEntity = ExampleEntity(context!!)
        deleteEntity.loadFromLocalStorage("ExampleEntity#1", deleteEntity)
        deleteEntity.removeFromLocalStorage()

        // #4 Get all Entities with the same EntityType
        val entities = LocalStorage.getAll(context!!, ExampleEntity::class.java) as List<*> as List<ExampleEntity>
    }
}