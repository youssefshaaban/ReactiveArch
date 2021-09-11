package com.example.reactivearch.core.navigation

import androidx.fragment.app.FragmentActivity
import com.example.reactivearch.R
import com.example.reactivearch.core.navigation.Screen.MAP
import com.example.reactivearch.core.navigation.Screen.RESTUARANT_DETAIL
import com.example.reactivearch.ui.feature.map.RestaurantMapsFragment
import com.example.reactivearch.ui.feature.restaurant.RestaurantDetailFragment
import javax.inject.Inject

class AppNavigatorImp @Inject constructor(val activity: FragmentActivity) : AppNavigator {
  override fun navigateTo(screen: Screen) {
    val fragment = when (screen) {
      MAP -> RestaurantMapsFragment()
      RESTUARANT_DETAIL->RestaurantDetailFragment()
    }
    activity.supportFragmentManager.beginTransaction().replace(
      R.id.homeContainer,
      fragment
    )
      .addToBackStack(fragment.javaClass.canonicalName)
      .commit()

  }
}