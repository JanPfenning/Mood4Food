package com.jrk.mood4food.model.local;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class LocalStorage {

    public static List<LocalEntity> getAll(Context context, Class entityClass/*, filter */){

        List<LocalEntity> entities = new ArrayList<>();
        try {
            LocalEntity entity = (LocalEntity) entityClass.newInstance();
            if(entity.hasEntitySet()) {
                SharedPreferences entitySetFile = context.getSharedPreferences(entityClass.getName(), 0);
                for (String key : entitySetFile.getAll().keySet()) {
                    try {
                        entity.loadFromLocalStorage(key, entity);
                        entities.add(entity);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                entity.loadFromLocalStorage(entityClass.getName(), entity);
                entities.add(entity);
            }
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return entities;

    }

}
