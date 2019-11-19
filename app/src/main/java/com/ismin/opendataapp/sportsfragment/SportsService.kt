package com.ismin.opendataapp.sportsfragment

import com.ismin.opendataapp.sportsfragment.database.places.PlaceEntity
import com.ismin.opendataapp.sportsfragment.database.sports.SportEntity
import retrofit2.Call
import retrofit2.http.GET

interface SportsService {
    @GET("sports")
    fun getAllSports(): Call<List<SportEntity>>

    @GET("places")
    fun getAllPlaces(): Call<List<PlaceEntity>>
}