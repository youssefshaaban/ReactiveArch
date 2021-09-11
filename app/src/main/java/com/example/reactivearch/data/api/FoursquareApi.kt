package com.example.reactivearch.data.api

import com.example.reactivearch.data.api_response.RestaurantsVenues
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FoursquareApi {
  @GET("v2/venues/search")
  fun getRestaurants(@Query("ll",encoded = true) ll:String) : Single<RestaurantsVenues>
}