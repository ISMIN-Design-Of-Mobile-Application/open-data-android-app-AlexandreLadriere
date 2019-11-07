package com.ismin.opendataapp.sportsfragment

import java.io.Serializable

data class Sport(
    val id: Int,
    val name: String,
    val tags: ArrayList<String>,
    val filters: ArrayList<String>
) : Serializable