package com.example.reactivearch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.reactivearch.R
import com.example.reactivearch.R.layout
import com.example.reactivearch.ui.feature.map.RestaurantMapsFragment

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)
    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction().replace(
        R.id.homeContainer, RestaurantMapsFragment()
      ).commit()
    }
  }
}