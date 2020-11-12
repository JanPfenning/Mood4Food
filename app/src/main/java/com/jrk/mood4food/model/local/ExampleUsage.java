package com.jrk.mood4food.model.local;

import android.content.Context;

import java.util.List;

public class ExampleUsage {

    public ExampleUsage(){

        // #1 Create new Entity & save to Local Storage

        Context context = null; // Application Context
        ExampleEntity newEntity = new ExampleEntity(context);
        newEntity.setExampleString("Test");
        // Save attribute values
        try {
            newEntity.saveToLocalStorage(newEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // #2 Load Entity from Storage and update its values

        ExampleEntity existingEntity = new ExampleEntity(context);
        try {
            existingEntity.loadFromLocalStorage("ExampleEntity#1", existingEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        existingEntity.setExampleBoolean(true);
        try {
            existingEntity.saveToLocalStorage(existingEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // #3 Delete Entity from LocalStorage

        ExampleEntity deleteEntity = new ExampleEntity(context);
        try {
            deleteEntity.loadFromLocalStorage("ExampleEntity#1", deleteEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        deleteEntity.removeFromLocalStorage();

        // #4 Get all Entities with the same EntityType

        List<ExampleEntity> entities = (List<ExampleEntity>)(List<?>) LocalStorage.getAll(context, ExampleEntity.class);

    }

}
