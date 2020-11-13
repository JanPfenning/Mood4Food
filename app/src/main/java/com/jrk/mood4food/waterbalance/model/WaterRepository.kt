package com.jrk.mood4food.waterbalance.model

class WaterRepository {
    public fun storeWaterBalance(waterBalance:Float){
        var progress:Float = (100/getWaterLevel())*waterBalance
        //TODO
    }
    public fun getCurrentWaterBalance():Float{
        return 33F
    }
    public fun getWaterLevel():Float{
        return 3.0F
    }
}