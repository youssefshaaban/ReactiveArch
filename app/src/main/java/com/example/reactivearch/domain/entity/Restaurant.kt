package com.example.reactivearch.domain.entity

data class Restaurant(
  val id: Int, val latitude: Double, val longitude: Double,
  val city: String?,
  val address: String?,
  val name: String
)