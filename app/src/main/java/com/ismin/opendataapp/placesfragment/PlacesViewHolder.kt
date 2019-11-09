package com.ismin.opendataapp.placesfragment

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ismin.opendataapp.R

class PlacesViewHolder(rootView: View, private val selectPlace: (Int) -> Unit) :
    RecyclerView.ViewHolder(rootView) {
    var placeName: TextView = rootView.findViewById(R.id.place_text_view_name)
    var placeImage: ImageView = rootView.findViewById(R.id.place_image_view)
    var placeAddress: TextView = rootView.findViewById(R.id.place_text_view_address)
    var placeDistance: TextView = rootView.findViewById(R.id.place_text_view_distance)
    private var container: ConstraintLayout =
        rootView.findViewById(R.id.recycler_view_place_constraint_layout)

    init {
        container.setOnClickListener {
            selectPlace(adapterPosition)
        }
    }
}