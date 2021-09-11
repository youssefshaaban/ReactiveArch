package com.example.reactivearch.data.api_response

data class Venue(
    val categories: List<Category>,
    val id: String,
    val location: Location,
    val name: String,
    val venuePage: VenuePage
)