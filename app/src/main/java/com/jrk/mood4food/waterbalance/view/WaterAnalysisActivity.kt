package com.jrk.mood4food.waterbalance.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.jrk.mood4food.NavBarActivity
import com.jrk.mood4food.R


class WaterAnalysisActivity: NavBarActivity() {

    private val MAX_X_VALUE = 7
    private val MAX_Y_VALUE = 50
    private val MIN_Y_VALUE = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_water_analysis)
        super.onCreate(savedInstanceState)

        // Please keep these design settings
        val chart = findViewById<BarChart>(R.id.fragment_verticalbarchart_chart)
        chart.setDrawGridBackground(false)
        chart.setDrawBorders(false)
        chart.axisLeft.setDrawAxisLine(false)
        chart.axisLeft.setDrawGridLines(false)
        chart.axisLeft.isEnabled = false
        chart.axisRight.setDrawAxisLine(false)
        chart.axisRight.setDrawGridLines(false)
        chart.axisRight.isEnabled = false
        chart.xAxis.textSize = 16F
        chart.xAxis.yOffset = -10F
        chart.xAxis.textColor = Color.WHITE
        chart.xAxis.setDrawAxisLine(false)
        chart.xAxis.setDrawGridLines(false)
        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        val xAxisLabel = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabel);

        val data = createChartData()
        chart.data = data
        chart.invalidate()

    }

    private fun createChartData(): BarData? {
        val values: ArrayList<BarEntry> = ArrayList()
        for (i in 0 until MAX_X_VALUE) {
            val x = i.toFloat()
            val y: Float = (MIN_Y_VALUE..MAX_Y_VALUE).random().toFloat()
            val bar = BarEntry(x, y)
            values.add(bar)
        }
        val set1 = BarDataSet(values, "")
        set1.valueTextColor = Color.WHITE
        set1.valueTextSize = 12F

        // Use dark color when aim not reached else light color
        set1.colors = listOf(
                resources.getColor(R.color.waterDark), // Mon
                resources.getColor(R.color.waterLight), // Tue
                resources.getColor(R.color.waterLight), // ...
                resources.getColor(R.color.waterLight),
                resources.getColor(R.color.waterDark),
                resources.getColor(R.color.waterDark),
                resources.getColor(R.color.waterDark)
        )

        val dataSets: ArrayList<IBarDataSet> = ArrayList()
        dataSets.add(set1)

        val data = BarData(dataSets)

        return data
    }

}