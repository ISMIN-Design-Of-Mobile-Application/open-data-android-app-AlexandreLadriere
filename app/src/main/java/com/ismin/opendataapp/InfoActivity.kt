package com.ismin.opendataapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val backButton = findViewById<ImageButton>(R.id.a_info_image_button_back).setOnClickListener {view ->
            finish()
        }

        curveImageViewCorner(findViewById<ImageView>(R.id.a_info_image_view_author1), 30F)
        curveImageViewCorner(findViewById<ImageView>(R.id.a_info_image_view_author2), 30F)
    }
}
