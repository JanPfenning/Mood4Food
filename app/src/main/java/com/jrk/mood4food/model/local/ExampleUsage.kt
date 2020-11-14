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

        // #2 Get all Entities with the same EntityType
        // Functionality to filter will come soon
        // Please always use this method to get one or more Entities
        val entities = LocalStorage.getAll(context!!, ExampleEntity::class.java) as List<*> as List<ExampleEntity>

        // #3 Update Entity
        val entity = entities.get(0);
        entity.exampleInt = 3
        entity.saveToLocalStorage(entity)

        // #4 Delete Entity
        entity.removeFromLocalStorage()

    }
}