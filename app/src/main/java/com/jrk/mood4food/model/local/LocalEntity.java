package com.jrk.mood4food.model.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;

public class LocalEntity {

    public String[] validDataTypes = new String[]{
            "class java.lang.String",
            "int",
            "boolean",
            "long",
            "float",
            "interface java.util.Set"
    };

    private String storageAddress;
    private String entityName;
    private Context context;
    private boolean hasEntitySet;

    private Field[] attributes;

    public LocalEntity(Context context, Class entity, boolean hasEntitySet) {
        this.context = context;
        this.entityName = entity.getName();
        this.attributes = entity.getDeclaredFields();
        this.hasEntitySet = hasEntitySet;

        for(Field attribute : attributes) {
            System.out.println(Arrays.asList(validDataTypes));
            System.out.println(attribute.getType());
            if(!Arrays.asList(validDataTypes).contains(attribute.getType().toString())){
                Log.e("LocalStorage", "The class '" + entityName + "' has attributes of data types which can not be stored in SharedPreferences. Valid data types are: " + Arrays.toString(validDataTypes));
            }
        }
    }

    public String getStorageAddress() {
        return storageAddress;
    }

    public String getEntityName(){
        return entityName;
    }

    public boolean hasEntitySet(){
        return hasEntitySet;
    }

    public void loadFromLocalStorage(String storageAddress, LocalEntity entity) throws IllegalAccessException {
        this.storageAddress = storageAddress;
        SharedPreferences entityFile = context.getSharedPreferences(storageAddress, 0);
        for(Field attribute : attributes) {
            switch (attribute.getType().toString()){
                case "class java.lang.String":
                    attribute.set(entity, entityFile.getString(attribute.getName(), null));
                    break;
                case "int":
                    attribute.setInt(entity, entityFile.getInt(attribute.getName(), 0));
                    break;
                case "boolean":
                    attribute.setBoolean(entity, entityFile.getBoolean(attribute.getName(), false));
                    break;
                case "long":
                    attribute.setLong(entity, entityFile.getLong(attribute.getName(), 0));
                    break;
                case "float":
                    attribute.setFloat(entity, entityFile.getFloat(attribute.getName(), 0));
                    break;
                case "interface java.util.Set":
                    attribute.set(entity, entityFile.getStringSet(attribute.getName(), null));
                    break;
            }

        }

    }

    public void saveToLocalStorage(LocalEntity entity) throws IllegalAccessException {
        if(storageAddress == null){
            if(hasEntitySet) {
                SharedPreferences entitySetFile = context.getSharedPreferences(entityName, 0);
                int entityNum = entitySetFile.getInt(entityName + ".length", 0);
                storageAddress = entityName + "#" + entityNum;
                entitySetFile.edit().putInt(entityName + ".length", entityNum + 1).putInt(storageAddress, entityNum).apply();
            }else{
                storageAddress = entityName;
            }
        }
        SharedPreferences entityFile = context.getSharedPreferences(entityName, 0);
        SharedPreferences.Editor editor = entityFile.edit();
        for(Field attribute : attributes){
            switch (attribute.getType().toString()){
                case "class java.lang.String":
                    editor.putString(attribute.getName(), (String) attribute.get(entity));
                    break;
                case "int":
                    editor.putInt(attribute.getName(), (int) attribute.get(entity));
                    break;
                case "boolean":
                    editor.putBoolean(attribute.getName(), (boolean) attribute.get(entity));
                    break;
                case "long":
                    editor.putLong(attribute.getName(), (long) attribute.get(entity));
                    break;
                case "float":
                    editor.putFloat(attribute.getName(), (float) attribute.get(entity));
                    break;
                case "interface java.util.Set":
                    editor.putStringSet(attribute.getName(), (Set<String>) attribute.get(entity));
                    break;
            }
        }
        editor.apply();
    }

    public void removeFromLocalStorage(){
        if(storageAddress != null){
            SharedPreferences entityFile = context.getSharedPreferences(storageAddress, 0);
            entityFile.edit().clear().apply();
            File file = new File(context.getFilesDir().getParent() + "/shared_prefs/" + storageAddress);
            file.delete();
            if(hasEntitySet){
                SharedPreferences entitySetFile = context.getSharedPreferences(entityName, 0);
                entityFile.edit().remove(storageAddress).apply();
            }
        }
    }
}
