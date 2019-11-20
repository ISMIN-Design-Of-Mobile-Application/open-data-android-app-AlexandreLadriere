package com.ismin.opendataapp.sportsfragment

import com.ismin.opendataapp.sportsfragment.database.SportEntity
import retrofit2.Call
import retrofit2.http.GET

interface SportsService {
    @GET("sports")
    fun getAllSports(): Call<List<SportEntity>>
}