package com.example.myapplication

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextStartTime = findViewById<TextInputEditText>(R.id.editTextStartTime)
        val editTextEndTime = findViewById<TextInputEditText>(R.id.editTextEndTime)
        editTextStartTime.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(8)
                .setMinute(0)
                .setTitleText("Select time of the class")
                .build()
            timePicker.show(supportFragmentManager, "timePicker")

            timePicker.addOnPositiveButtonClickListener {
                val amPm = if (timePicker.hour < 12) "AM" else "PM"

                val hour =
                    if (timePicker.hour == 0) 12 else if (timePicker.hour > 12) timePicker.hour - 12 else "0" + timePicker.hour
                val minute =
                    if (timePicker.minute < 10) "0${timePicker.minute}" else timePicker.minute

                editTextStartTime.setText("${hour}:$minute $amPm")
            }

            timePicker.addOnNegativeButtonClickListener {
                editTextStartTime.setText("Cancelled")
            }

            timePicker.addOnCancelListener {
                editTextStartTime.setText("Cancelled")
            }


        }

    }


}