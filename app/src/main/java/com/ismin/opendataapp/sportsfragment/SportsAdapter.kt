package com.ismin.opendataapp.sportsfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ismin.opendataapp.R

class SportsAdapter(
    private val icons: ArrayList<Sport>,
    private val selectSport: (Int) -> Unit
) :
    RecyclerView.Adapter<CellarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellarViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_sports_item, parent,
            false
        )
        return CellarViewHolder(row, selectSport)
    }

    override fun onBindViewHolder(viewholder: CellarViewHolder, position: Int) {
        val (id, name, tags, filters) = this.icons[position]

        viewholder.sportName.text = name
        viewholder.sportImage.setImageResource(R.drawable.ic_sports_24px)
    }

    override fun getItemCount(): Int {
        return this.icons.size
    }
}