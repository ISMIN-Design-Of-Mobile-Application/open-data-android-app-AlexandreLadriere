package com.ismin.opendataapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.ismin.opendataapp.placesfragment.database.PlaceEntity

@Suppress("CAST_NEVER_SUCCEEDS")
class PlaceDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_details)
        val place = intent.getSerializableExtra(Intent.EXTRA_TEXT) as PlaceEntity

        findViewById<TextView>(R.id.a_place_details_text_view_place_title).text = place.name
        findViewById<TextView>(R.id.a_place_details_text_view_place_address).text = place.address
        findViewById<TextView>(R.id.a_place_details_text_view_place_distance).text = place.distance + " km"
        findViewById<TextView>(R.id.a_place_details_text_view_place_coordinates).text = place.latitude + ", " + place.longitude
        findViewById<TextView>(R.id.a_place_details_text_view_place_link).text = place.website
        findViewById<ImageView>(R.id.a_place_details_image_view_photo).setImageResource(place.image)

        val closeButton = findViewById<Button>(R.id.a_place_details_button_close).setOnClickListener { view ->
            finish()
        }
    }
}
