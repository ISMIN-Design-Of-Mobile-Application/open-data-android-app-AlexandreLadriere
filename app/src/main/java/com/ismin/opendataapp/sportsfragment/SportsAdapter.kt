package com.ismin.opendataapp.sportsfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ismin.opendataapp.R
import com.ismin.opendataapp.sportsfragment.database.SportEntity

class SportsAdapter(
    private val sportsList: ArrayList<SportEntity>,
    private val selectSport: (Int, Boolean) -> Unit
) :
    RecyclerView.Adapter<SportsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportsViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_sports_item, parent,
            false
        )
        return SportsViewHolder(row)
    }

    override fun onBindViewHolder(viewholder: SportsViewHolder, position: Int) {
        val (id, name) = this.sportsList[position]
        viewholder.sportName.text = name
        viewholder.sportName.setOnCheckedChangeListener { buttonView, isChecked ->
            selectSport(id, isChecked)
        }
        viewholder.sportName.isChecked = this.sportsList[position].isEnabled
    }

    override fun getItemCount(): Int {
        return this.sportsList.size
    }
}