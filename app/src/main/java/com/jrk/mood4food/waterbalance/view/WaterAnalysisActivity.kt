package com.jrk.mood4food.waterbalance.view

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.jrk.mood4food.NavBarActivity
import com.jrk.mood4food.R
import com.jrk.mood4food.model.ModelModule
import com.jrk.mood4food.waterbalance.controller.WaterAnalyserController
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class WaterAnalysisActivity : NavBarActivity(), WaterAnalyserView {


    private val model = ModelModule.dataAccessLayer
    private val controller = WaterAnalyserController(model)
    private var weekOfYear = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_water_analysis)
        super.onCreate(savedInstanceState)
        controller.bind(this)
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
        chart.axisLeft.axisMaximum = model.getWaterRepository().getWaterLevel()
        val xAxisLabel = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabel);
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        val dateFormatted = formatter.format(Calendar.getInstance().time)
        val cal = getCalenderFromDate(dateFormatted)
        weekOfYear = getCalenderWeekFromDate(cal)
        findViewById<TextView>(R.id.week).text = """Week $weekOfYear"""
        chart.data = createChartData()
        chart.invalidate()

        findViewById<TextView>(R.id.waterlevel).text = model.getWaterRepository().getWaterLevel().toString()

        findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }
        findViewById<ImageView>(R.id.backward_week).setOnClickListener {
            weekOfYear--
            findViewById<TextView>(R.id.week).text = """Week $weekOfYear"""
            chart.data = createChartData()
            chart.invalidate()
        }
        findViewById<ImageView>(R.id.forward_week).setOnClickListener {
            weekOfYear++
            findViewById<TextView>(R.id.week).text = """Week $weekOfYear"""
            chart.data = createChartData()
            chart.invalidate()
        }
    }

    private fun createChartData(): BarData? {

        val ret = model.getWaterRepository().getWaterEntityFromWeekOfYear(weekOfYear)
        val entities = ret.first
        val isReached = ret.second


        val values: ArrayList<BarEntry> = ArrayList()
        val colors: ArrayList<Int> = ArrayList()
        for (i in entities.indices) {
            //Copyright Leonhard Stengel---
            var rico = entities[i].dayOfWeek.toFloat() - 2
            if (rico < 0)
                rico = 7 + rico
            val bar = BarEntry(rico, entities[i].waterBalance)
            //-----------------------------
            if (isReached[i]) {
                colors.add(resources.getColor(R.color.waterLight))
            } else {
                colors.add(resources.getColor(R.color.waterDark))
            }

            values.add(bar)
        }
        val set1 = BarDataSet(values, "")
        set1.colors = colors
        set1.valueTextColor = Color.WHITE
        set1.valueTextSize = 12F
        val dataSets: ArrayList<IBarDataSet> = ArrayList()
        dataSets.add(set1)
        return BarData(dataSets)
    }

    private fun getCalenderFromDate(currentDate: String): Calendar {
        val format = "dd.MM.yyyy"

        val df = SimpleDateFormat(format)
        val date = df.parse(currentDate)

        val cal = Calendar.getInstance()
        cal.time = date
        return cal
    }

    private fun getDayofWeek(cal: Calendar): Int {
        return cal[Calendar.DAY_OF_WEEK]
    }

    private fun getCalenderWeekFromDate(cal: Calendar): Int {
        return cal[Calendar.WEEK_OF_YEAR]
    }

}