package com.didaagency.adopteunelivraison.view.baseViews

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.utils.Constants

open class BaseLocationActivity : BaseActivity() {

    protected var fusedLocationClient: FusedLocationProviderClient? = null
    protected var locationRequest: LocationRequest? = null


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest
            .create()
            .setInterval(1000)
            .setFastestInterval(500) /*.setMaxWaitTime(TimeUnit.MINUTES.toMillis(2))*/
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

    }
    private var showMockLocationDialog: Boolean = true



    //Initialize location call back
    var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            try {
                Log.d("LocationCallback", "Callback started")
                //Initialize location1
                val location1 = locationResult.lastLocation
                //showToast(location1.toString());
                val location = location1!!.latitude.toString() + "," + location1.longitude
                // Log.i("New Location", location);
                Constants.CURRENT_LOCATION = location1
                // sharedPreferenceUtil.saveValue(location, AppConstants.USER_LOCATION)
                if (isMockLocation(location1) && showMockLocationDialog) {
                    showMockLocationDialog = false
                    showBlockerDialog(
                        this@BaseLocationActivity,
                        resources.getString(R.string.str_mock_location_enabled),
                        resources.getString(R.string.str_mock_location_explanation)
                    )
                }
            } catch (e: Exception) {
                Log.e("Exception", e.message!!)
            }
        }
    }

    fun isMockLocation(location: Location?): Boolean {
        return location != null && location.isFromMockProvider
    }



    fun onClickRequestPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                fusedLocationClient!!.requestLocationUpdates(
                    locationRequest!!,
                    locationCallback,
                    Looper.getMainLooper()
                )
            } catch (securityException: Exception) {
                Log.e(
                    this.javaClass.simpleName,
                    "Lost location permissions. Couldn't remove updates. " + securityException.message
                )
            }
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Snackbar.make(
                findViewById(android.R.id.content),
                "Permission is required for app to work properly",
                Snackbar.LENGTH_INDEFINITE
            ).setAction(
                "Ok"
            ) { view: View? ->
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }.show()
        } else {
            requestPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Log.i("Permission: ", "Granted")
                try {
                    fusedLocationClient!!.requestLocationUpdates(
                        locationRequest!!,
                        locationCallback,
                        Looper.getMainLooper()
                    )
                } catch (securityException: SecurityException) {
                    Log.e(
                        this.javaClass.simpleName,
                        "Lost location permissions. Couldn't remove updates. " + securityException.message
                    )
                }
            } else {
                Log.i("Permission: ", "Denied")
                showToast("Permission is required for app to work properly. Please enable it in settings")
            }
        }

}