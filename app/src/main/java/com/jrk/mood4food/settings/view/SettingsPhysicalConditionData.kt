package com.jrk.mood4food.settings.view

import com.jrk.mood4food.settings.Gender

class SettingsPhysicalConditionData {
    var age = 0
    var gender:Gender = Gender.Fail
    var currentBodyWeight:Float = 0F;
    var bodySize = 0
    var aimBodyWeight:Float = 0F;
    var weightChange:Float = 0F;
    var weightChangePerMonth:Float = 0F;
    var physicalActivity:String = "";
    var waterPerDay = 0F
    var caloriesPerDay = 0
    var carbohydratesPerDay = 0
    var proteinPerDay = 0
    var fatPerDay = 0

}