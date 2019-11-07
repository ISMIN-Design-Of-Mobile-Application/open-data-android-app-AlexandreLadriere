package com.ismin.opendataapp.sportsfragment

import com.ismin.opendataapp.R
import java.io.Serializable

data class Sport (val name: String?,
                  val image: Int = R.drawable.ic_sports_24px,
                  val id: Int?
) : Serializable