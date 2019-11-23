package com.ismin.opendataapp.placesfragment.photoreference

import com.ismin.opendataapp.placesfragment.PlaceService
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoReferenceService {
    @GET("findplacefromtext/json")
    fun getPhotoReferenceFromName(@Query("input") placeName: String,
                                  @Query("inputtype") inputType: String = "textquery",
                                  @Query("fields") fieldType: String = "photos",
                                  @Query("key") key: String = "AIzaSyDTvIgeeBFG2uXzuraR7TVbDs4TSZL_IGg") : Observable<PhotoReferenceModel.Result>

    companion object {
        fun create(): PhotoReferenceService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://maps.googleapis.com/maps/api/place/")
                .build()

            return retrofit.create(PhotoReferenceService::class.java)
        }
    }
}