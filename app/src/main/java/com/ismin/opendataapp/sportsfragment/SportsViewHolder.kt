package com.ismin.opendataapp.sportsfragment

import android.view.View
import android.widget.CheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ismin.opendataapp.R

class SportsViewHolder(
    rootView: View
) : RecyclerView.ViewHolder(rootView) {

    var sportName: CheckBox = rootView.findViewById(R.id.sport_checkbox)
    var container: ConstraintLayout = rootView.findViewById(R.id.sport_grid_view)

}