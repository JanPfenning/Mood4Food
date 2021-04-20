package com.jrk.mood4food.waterbalance.model

import android.content.Context
import com.jrk.mood4food.model.localStorage.LocalEntity

class SettingsEntity(context: Context) : LocalEntity(context, SettingsEntity::class.java, false) {
    var age = 0
    var gender = "Male"
    var currentBodyWeight: Float = 0F;
    var bodySize = 0
    var aimBodyWeight:Float = 0F;
    var weightChange:Float = 0F;
    var weightChangePerMonth:Float = 0F;
    var physicalActivity:String = "Normal";
    var waterPerDay = 0F
    var caloriesPerDay = 0
    var carbohydratesPerDay = 0
    var proteinPerDay = 0
    var fatPerDay = 0


}