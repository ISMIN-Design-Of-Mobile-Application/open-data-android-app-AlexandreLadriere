package com.ismin.opendataapp.placesfragment

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("places")
    fun getPlaces(@Query("origin") origin: String,
                  @Query("page") page: String,
                  @Query("radius") radius: String,
                  @Query("sports") sports: String) : Observable<PlaceModel.Result>

    companion object {
        fun create(): PlaceService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://sportplaces.api.decathlon.com/api/v1/")
                .build()

            return retrofit.create(PlaceService::class.java)
        }
    }

}
