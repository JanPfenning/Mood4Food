package com.jrk.mood4food.onboarding.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.jrk.mood4food.App
import com.jrk.mood4food.R
import com.jrk.mood4food.settings.view.SettingsActivity

class OnboardingActivity() : AppCompatActivity() {

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

    private fun lastPage(){
        if(pageIndex - 1 > -1) {
            pageIndex--
            initPage()
        }
    }

    private fun nextPage(){
        pageIndex++
        if(pageIndex < pages.size) {
            initPage()
        }else{
            finishOnboarding()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initPage(){
        setContentView(pages[pageIndex])

        val skipBtn = findViewById<Button>(R.id.skip_btn)
        skipBtn.setOnClickListener(View.OnClickListener {
            finishOnboarding()
        })
        val nextBtn = findViewById<LinearLayout>(R.id.next_btn)
        nextBtn.setOnClickListener(View.OnClickListener {
            nextPage()
        })
        val layout = findViewById<ConstraintLayout>(R.id.onboarding_layout)
        layout.setOnTouchListener(object: OnSwipeTouchListener(App.getContext()) {
            override fun onSwipeLeft() {
                nextPage()
            }
            override fun onSwipeRight() {
                lastPage()
            }
        })
    }

    private fun finishOnboarding(){
        val onboardingFile = getSharedPreferences("onboarding", 0)
        onboardingFile.edit().putBoolean("firstAppUsage", false).apply()

        // Set base settings here
        // ...

        finish()
        startActivity(Intent(this, SettingsActivity::class.java))
    }
}