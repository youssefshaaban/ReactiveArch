package com.example.reactivearch.core.navigation

interface AppNavigator {
  fun navigateTo(screen: Screen)
}

enum class Screen{
  MAP,RESTUARANT_DETAIL
}