package com.example.reactivearch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.reactivearch.R
import com.example.reactivearch.R.layout
import com.example.reactivearch.core.navigation.AppNavigator
import com.example.reactivearch.core.navigation.Screen.MAP
import com.example.reactivearch.ui.feature.map.RestaurantMapsFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  @Inject
  lateinit var appNavigator: AppNavigator
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)
    if (savedInstanceState == null) {
      appNavigator.navigateTo(MAP)
    }
  }

  override fun onBackPressed() {
    super.onBackPressed()
    if (supportFragmentManager.backStackEntryCount == 0) {
      finish()
    }
  }
}