package com.jrk.mood4food

import android.os.Bundle

class SettingsActivity : NavBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_settings)
        super.onCreate(savedInstanceState)
        println("opened settings")
    }
}