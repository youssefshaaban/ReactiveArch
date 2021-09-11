package com.example.reactivearch.domain.interactor

import com.example.reactivearch.core.common.DataState
import com.example.reactivearch.domain.dto.LocationDto
import com.example.reactivearch.domain.entity.Restaurant
import com.example.reactivearch.domain.entity.error.ErrorHandler
import com.example.reactivearch.domain.entity.error.Failure
import com.example.reactivearch.domain.repositrory.RestaurantRepository
import io.reactivex.Single
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.UnknownHostException
import javax.inject.Inject

class GetRestaurantUseCase @Inject constructor(private val repository: RestaurantRepository) :
  UseCase<LocationDto, Single<DataState<List<Restaurant>>>>, ErrorHandler {
  override fun execute(param: LocationDto): Single<DataState<List<Restaurant>>> {
    return repository.getRestaurants(locationDto = param)
      .onErrorReturn { return@onErrorReturn DataState.Error(getError(throwable = it)) }
  }

  override fun getError(throwable: Throwable): Failure {
    HttpURLConnection.HTTP_NOT_FOUND
    return when (throwable) {
      is UnknownHostException -> Failure.NetworkConnection
      is HttpException -> {
         when (throwable.code()) {
           HttpURLConnection.HTTP_NOT_FOUND -> {
            Failure.ServerError.NotFound
          }
           HttpURLConnection.HTTP_FORBIDDEN -> {
            Failure.ServerError.AccessDenied
          }
           HttpURLConnection.HTTP_UNAVAILABLE -> {
            Failure.ServerError.ServiceUnavailable
          }
          else -> Failure.UnknownError
        }
      }
      else -> Failure.UnknownError
    }
  }
}