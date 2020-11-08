package com.jrk.mood4food

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.john.waveview.WaveView


class WaterBalanceActivity : AppCompatActivity() {
    private val sharedPrefFile = "mood4Food"
    private var  waterlevel:Float = 0.0F;
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_water_balance)
        val sharedPref: SharedPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        waterlevel = getWaterlevelP();
        update();
        findViewById<AppCompatImageView>(R.id.addwater).setOnClickListener{
            val builder1 = AlertDialog.Builder(this)
            builder1.setMessage("Wie viel hast du getrunken")
            builder1.setCancelable(true)
            val input = EditText(this)

            builder1.setView(input)
            builder1.setPositiveButton(

                    "Add", DialogInterface.OnClickListener { dialog, id ->

                val inputText = input.text.toString().toFloat()
                waterlevel += inputText.toFloat()
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
    fun getWaterlevelP():Float{
        val sharedPref: SharedPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val waterlevel =   sharedPref.getFloat("waterLevel", 0.0F)
        return  waterlevel


    }
    fun setWaterlevelP(level:Float){
        val sharedPref: SharedPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putFloat("waterLevel", level)
        editor.commit()
    }


}