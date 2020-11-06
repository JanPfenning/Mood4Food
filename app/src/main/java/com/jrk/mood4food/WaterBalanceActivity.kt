package com.jrk.mood4food

import android.os.Bundle

class WaterBalanceActivity : NavBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_water_balance)
        super.onCreate(savedInstanceState)
        println("opened waterbalance")
    }

}