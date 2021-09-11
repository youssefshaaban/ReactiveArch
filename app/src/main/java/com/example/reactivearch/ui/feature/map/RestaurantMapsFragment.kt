package com.example.reactivearch.ui.feature.map

import android.Manifest
import android.content.Intent
import android.net.Uri

import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.reactivearch.R
import com.example.reactivearch.core.common.BaseFragmentWithLocation
import com.example.reactivearch.core.navigation.AppNavigator
import com.example.reactivearch.core.navigation.Screen.RESTUARANT_DETAIL
import com.example.reactivearch.ui.feature.restaurant.RestaurantDetailFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.RuntimePermissions
import timber.log.Timber
import javax.inject.Inject

@RuntimePermissions
@AndroidEntryPoint
class RestaurantMapsFragment : BaseFragmentWithLocation(),GoogleMap.OnMarkerClickListener {
  @Inject
  lateinit var appNavigator: AppNavigator
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_restaurant_maps, container, false)
  }

  private val callback=OnMapReadyCallback{
    googleMap->
    val sydeny=LatLng(-34.0,151.0)
    googleMap.addMarker(MarkerOptions().position(sydeny).title("Marker in sydney"))
    googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydeny))
    googleMap.setOnMarkerClickListener(this)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
    mapFragment?.getMapAsync(callback)
    getCurrentLocationWithPermissionCheck()

  }

  // MARK -- handle permission
  @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
  fun getCurrentLocation() {
    if (isLocationEnable()){
      getLastLocation {
        Timber.e("available lat, long : %s,%s",it.latitude,it.longitude)
      }
    }else{
      MaterialAlertDialogBuilder(getRootActivity())
        .setTitle(getString(R.string.location_not_enable))
        .setMessage(getString(R.string.enableLocation))
        .setPositiveButton(getString(R.string.enable)){
          dialog,_->
          // open_setting_screen
          openSettingScree()
          dialog.dismiss()
        }
        .setNegativeButton(getString(R.string.deny)){
            dialog,_->
          dialog.dismiss()
        }
        .show()
    }
  }

  private fun openSettingScree() {
    locationSettingScreen.launch(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
  }

  private val applicationLocationSetting=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
    getCurrentLocation()
  }
  private val locationSettingScreen=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
    getCurrentLocation()
  }

  @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
  fun showRationaleForLocation(request: PermissionRequest) {
    MaterialAlertDialogBuilder(getRootActivity())
      .setMessage(getString(R.string.location_alert))
      .setPositiveButton(getString(R.string.accept)){
          dialog,_->
        request.proceed()
        dialog.dismiss()
      }
      .setNegativeButton(getString(R.string.deny)){
          dialog,_->
        request.cancel()
        dialog.dismiss()
      }
      .show()
  }

  @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
  fun onDenyAskLocation() {
    Toast.makeText(getRootActivity(),getString(R.string.location_denied),Toast.LENGTH_SHORT).show()
  }

  @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
  fun onLocationNeverAskAgain() {
    val openLocationSetting=Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    openLocationSetting.data= Uri.fromParts("package",activity?.packageName,null)
    applicationLocationSetting.launch(openLocationSetting)

  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    this.onRequestPermissionsResult(requestCode,grantResults)
  }

  override fun onMarkerClick(p0: Marker?): Boolean {
     appNavigator.navigateTo(RESTUARANT_DETAIL)
    return false
  }
  // MARK end
}