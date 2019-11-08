package com.ismin.opendataapp.placesfragment

import com.ismin.opendataapp.R
import java.io.Serializable

data class Place(val name: String, val address: String, val distance: String, val latitude: String, val longitude: String, val website: String = "https://www.jcchevalier.fr/", val image: Int = R.drawable.stade_velodrome) : Serializable