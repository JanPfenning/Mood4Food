package com.jrk.mood4food.onboarding.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.jrk.mood4food.R
import com.jrk.mood4food.home.view.MainActivity

class OnboardingActivity : AppCompatActivity() {

    var pageIndex = -1
    private val pages = intArrayOf(
            R.layout.activity_onboarding,
            R.layout.onboarding_local_recipes,
            R.layout.onboarding_global_recipes,
            R.layout.onboarding_recommendations,
            R.layout.onboarding_water,
            R.layout.onboarding_settings
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nextPage()
    }

    private fun nextPage(){
        pageIndex++
        if(pageIndex < pages.size) {
            setContentView(pages[pageIndex])

            val skipBtn = findViewById<Button>(R.id.skip_btn)
            skipBtn.setOnClickListener(View.OnClickListener {
                finishOnboarding()
            })
            val nextBtn = findViewById<LinearLayout>(R.id.next_btn)
            nextBtn.setOnClickListener(View.OnClickListener {
                nextPage()
            })
        }else{
            finishOnboarding()
        }
    }

    private fun finishOnboarding(){
        // TODO set initial data
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }
}