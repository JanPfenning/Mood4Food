package com.jrk.mood4food

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView


class WaterBalanceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_water_balance)
        findViewById<AppCompatImageView>(R.id.addwater).setOnClickListener{
            val builder1 = AlertDialog.Builder(this)
            builder1.setMessage("Wie viel hast du getrunken")
            builder1.setCancelable(true)
            val input = EditText(this)

            builder1.setView(input)
            builder1.setPositiveButton(

                    "Add", DialogInterface.OnClickListener { dialog, id ->
                    val inputText = input.text.toString().toDouble()
                      
            })


            builder1.setNegativeButton(
                    "Cancel"
            ) { dialog, id -> dialog.cancel() }

            val alert11 = builder1.create()
            alert11.show()
        }

    }

}