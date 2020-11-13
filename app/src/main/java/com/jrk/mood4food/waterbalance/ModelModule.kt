package com.jrk.mood4food.waterbalance

object ModelModule {
    val dataAccessLayer: DataAccessLayer by lazy { dataAccessLayer() }
    private fun dataAccessLayer() = DataAccessLayer()

}