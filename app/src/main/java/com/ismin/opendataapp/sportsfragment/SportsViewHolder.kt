package com.ismin.opendataapp.sportsfragment

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ismin.opendataapp.R

class CellarViewHolder(
    rootView: View,
    private val selectSport: (Int) -> Unit
) : RecyclerView.ViewHolder(rootView) {

    var sportName: TextView = rootView.findViewById(R.id.sport_text_view)
    var sportImage: ImageView = rootView.findViewById(R.id.sport_image_view)
    private var container: ConstraintLayout = rootView.findViewById(R.id.sport_grid_view)

    init {
        container.setOnClickListener {
            selectSport(adapterPosition)
        }
    }
}