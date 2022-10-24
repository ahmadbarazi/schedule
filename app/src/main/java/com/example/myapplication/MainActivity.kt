package com.example.myapplication

import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract.CalendarAlerts
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import com.google.android.material.timepicker.TimeFormat.CLOCK_12H
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import kotlin.math.min

class MainActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {

    private val calendar = Calendar.getInstance()
    private val formatter = SimpleDateFormat("d mm, yyyy hh:mm a", Locale.US)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val editTextStartTime = findViewById<TextInputEditText>(R.id.editTextStartTime)
        val editTextEndTime = findViewById<TextInputEditText>(R.id.editTextEndTime)

        val textInputLayoutStartTime = findViewById<TextInputLayout>(R.id.TextInputLayoutStartTime)
        val textInputLayoutEndTime = findViewById<TextInputLayout>(R.id.TextInputLayoutEndTime)

        textInputLayoutStartTime.setEndIconOnClickListener() {
            editTextStartTime.setText("")
        }
        textInputLayoutEndTime.setEndIconOnClickListener() {
            editTextEndTime.setText("")
        }


        editTextStartTime.setOnClickListener {
             val timePicker = MaterialTimePicker.Builder()
            .setHour(calendar.get(Calendar.HOUR_OF_DAY))
            .setMinute(calendar.get(Calendar.MINUTE))
            .setTimeFormat(CLOCK_12H)
            .setInputMode(INPUT_MODE_CLOCK)
            .build()
            timePicker.show(supportFragmentManager,"timepicker")

            timePicker.addOnPositiveButtonClickListener {
                val hour = timePicker.hour
                val minute = timePicker.minute

                if (editTextEndTime.text.toString().isNotEmpty()){
                    if (timePicker.hour > editTextEndTime.text.toString().split(":")[0].toInt()) {
                        Toast.makeText(this, "Start time cannot be greater than end time", Toast.LENGTH_SHORT).show()
                    }
                    else if (timePicker.hour == editTextEndTime.text.toString().split(":")[0].toInt() && timePicker.minute > editTextEndTime.text.toString().split(":")[1].toInt()) {
                        Toast.makeText(this, "Start time cannot be greater than end time", Toast.LENGTH_SHORT).show()
                    }
                    else if (editTextEndTime.text.toString().split(":")[0].toInt() == 0 && editTextEndTime.text.toString().split(":")[1].toInt() == 0) {
                        Toast.makeText(this, "End time cannot be empty", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        editTextStartTime.setText("$hour:$minute")
                    }

                }
                else {
                    editTextStartTime.setText("$hour:$minute")
                }
            }


        }


        editTextEndTime.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                .setMinute(calendar.get(Calendar.MINUTE))
                .setTimeFormat(CLOCK_12H)
                .setInputMode(INPUT_MODE_CLOCK)
                .build()
            timePicker.show(supportFragmentManager, "timePicker")

            timePicker.addOnPositiveButtonClickListener {
                val hour = timePicker.hour
                val minute = timePicker.minute

                if (editTextStartTime.text.toString().isEmpty()){
                    Toast.makeText(this,"Enter Start Time first",Toast.LENGTH_LONG).show()
                }
                else {
                    if (timePicker.hour < editTextStartTime.text.toString().split(":")[0].toInt()) {
                        Toast.makeText(this, "End time cannot be less than start time", Toast.LENGTH_SHORT).show()
                    }
                    else if (timePicker.hour == editTextStartTime.text.toString().split(":")[0].toInt() && timePicker.minute < editTextStartTime.text.toString().split(":")[1].toInt()) {
                        Toast.makeText(this, "End time cannot be less than start time", Toast.LENGTH_SHORT).show()
                    }
                    else if (timePicker.hour == editTextStartTime.text.toString().split(":")[0].toInt() && timePicker.minute == editTextStartTime.text.toString().split(":")[1].toInt()) {
                        Toast.makeText(this, "End time cannot be equal to start time", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        editTextEndTime.setText("$hour:$minute")
                    }
                }
            }

            timePicker.addOnNegativeButtonClickListener {
                return@addOnNegativeButtonClickListener
            }

            timePicker.addOnCancelListener {
                return@addOnCancelListener
            }


        }

    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calendar.apply {
            set(Calendar.HOUR_OF_DAY,hourOfDay)
            set(Calendar.MINUTE,minute)
        }
    }


}