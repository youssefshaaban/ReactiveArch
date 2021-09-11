package com.example.reactivearch.domain.repositrory

import com.example.reactivearch.core.common.DataState
import com.example.reactivearch.domain.dto.LocationDto
import com.example.reactivearch.domain.entity.Restaurant
import io.reactivex.Single

interface RestaurantRepository {
  fun getRestaurants(locationDto: LocationDto):Single<DataState<List<Restaurant>>>
}