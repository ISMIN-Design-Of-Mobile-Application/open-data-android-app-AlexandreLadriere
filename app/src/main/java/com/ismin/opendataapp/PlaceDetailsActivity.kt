package com.ismin.opendataapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PlaceDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_details)
        val closeButton = findViewById<Button>(R.id.a_place_details_button_close).setOnClickListener { view ->
            finish()
        }
    }
}
