package com.ismin.opendataapp.placesfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ismin.opendataapp.R
import com.ismin.opendataapp.placesfragment.database.PlaceEntity

class PlacesAdapter(private val places: ArrayList<PlaceEntity>, private val selectPlace: (Int) -> Unit) :
    RecyclerView.Adapter<PlacesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val row = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_place_item, parent, false)
        return PlacesViewHolder(row, selectPlace)
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        val (id, name, address, distance, latitude, longitude, website, image) = this.places[position]
        holder.placeAddress.text = address
        holder.placeDistance.text = distance + " km"
        holder.placeName.text = name
        holder.placeImage.setImageResource(image)
    }

    override fun getItemCount(): Int {
        return this.places.size
    }
}
