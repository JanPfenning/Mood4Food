package com.jrk.mood4food

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_water_balance)
        startActivity(Intent(this,WaterBalanceActivity::class.java))
    }
}