package com.ismin.opendataapp.sportsfragment

import retrofit2.Call
import retrofit2.http.GET

interface SportsService {
    @GET("sports")
    fun getAllSports(): Call<List<Sport>>
}