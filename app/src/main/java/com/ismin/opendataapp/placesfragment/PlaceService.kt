package com.ismin.opendataapp.placesfragment

import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface PlaceService {
    @GET("places")
    fun getPlaces(@Query("origin") origin: String,
                  @Query("radius") radius: String,
                  @Query("sports") sports: String) }//: Observable<PlaceServerObject.Result>
