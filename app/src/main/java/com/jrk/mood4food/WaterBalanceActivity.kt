package com.jrk.mood4food

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.john.waveview.WaveView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class WaterBalanceActivity : NavBarActivity() {
    private val sharedPrefFile = "mood4Food"
    private var  waterlevel:Float = 0.0F;

    @RequiresApi(Build.VERSION_CODES.O)
    private  var currentD = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_balance)
        val sharedPref: SharedPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        waterlevel = getWaterlevelP();
        update();
        findViewById<AppCompatImageView>(R.id.addwater).setOnClickListener{
            val builder1 = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val view = inflater.inflate(R.layout.wateradd_dialog,null)
            builder1.setView(view)

            builder1.setMessage("Wie viel hast du getrunken")
            builder1.setCancelable(true)
            var wateradd:Float = 0.0F

            val seekBar = view.findViewById<SeekBar>(R.id.seekBar)

            seekBar?.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    view.findViewById<TextView>(R.id.textView).text = (p1 * 0.25).toString() + " Liter"
                    wateradd = (p1 * 0.25).toFloat()

                }

                override fun onStartTrackingTouch(p0: SeekBar?) {}

                override fun onStopTrackingTouch(p0: SeekBar?) {}
            })
            builder1.setPositiveButton(

                    "Add", DialogInterface.OnClickListener { dialog, id ->
                waterlevel += wateradd
                update()
                setWaterlevelP(waterlevel)

            })
            builder1.setNegativeButton(
                    "Cancel"
            ) { dialog, id -> dialog.cancel() }

            val alert11 = builder1.create()
            alert11.show()
        }

    }
    fun update(){

        val progress = (waterlevel)* (100/3)
        findViewById<WaveView>(R.id.waveView).setProgress(progress.toInt())
        findViewById<TextView>(R.id.waterlevelPercentage).setText(progress.toInt().toString()+ " %")
      
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getWaterlevelP():Float{
        val sharedPref: SharedPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val waterlevel =   sharedPref.getFloat("waterLevel"+currentD, 0.0F)
        return  waterlevel


    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun setWaterlevelP(level:Float){
        val sharedPref: SharedPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putFloat("waterLevel"+currentD, level)
        editor.commit()
    }


}