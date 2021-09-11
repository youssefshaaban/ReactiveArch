package com.example.reactivearch.core.common

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

open class BaseFragmentWithLocation : Fragment() {
  lateinit var fusedLocationClient: FusedLocationProviderClient
  var locationCallback: LocationCallback? = null
  var locationRequest: LocationRequest? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    fusedLocationClient =
      LocationServices.getFusedLocationProviderClient(getRootActivity())
  }

  fun getRootActivity(): FragmentActivity = activity as FragmentActivity

  @SuppressLint("MissingPermission")
  fun getLastLocation(onLocationAvailable: (Location) -> Unit) {
    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
      if (location != null) {
        onLocationAvailable(location)
      } else {
        createReauestLocation(onLocationAvailable)
      }
    }
  }

  private fun createReauestLocation(onLocationAvailable: (Location) -> Unit) {
    locationRequest = LocationRequest.create().apply {
      interval = 10000
      fastestInterval = 5000
      priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
    locationCallback = object : LocationCallback() {
      override fun onLocationResult(locationResult: LocationResult?) {
        locationResult ?: return
        for (location in locationResult.locations) {
          onLocationAvailable(location)
        }
      }
    }
  }

  override fun onResume() {
    super.onResume()
    startLocationUpdates()
  }

  override fun onPause() {
    super.onPause()
    stopLocationUpdates()
  }

  @SuppressLint("MissingPermission")
  private fun startLocationUpdates() {
    if (locationCallback != null && locationRequest != null)
      fusedLocationClient.requestLocationUpdates(
        locationRequest,
        locationCallback,
        Looper.getMainLooper()
      )
  }

  private fun stopLocationUpdates() {
    if (locationCallback != null)
      fusedLocationClient.removeLocationUpdates(locationCallback)
  }

  fun isLocationEnable(): Boolean {
    val locationManager =
      getRootActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
      LocationManager.NETWORK_PROVIDER
    )
  }
}