package com.jrk.mood4food.settings

enum class PhysicalActivity(val text: String) {

    Lowest("O Tage Sport"), Low("1-3 Tage Sport"), Middle("3-5 Tage Sport"), High("6-7 Tage Sport"), Highest("Extreme Belastung");


    companion object {
        private val map = PhysicalActivity.values().associateBy(PhysicalActivity::text)
        fun getByText(type: String) = map[type]

    }

}
