package com.didaagency.adopteunelivraison.view.dashboard

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.databinding.ActivityHomeBinding
import com.didaagency.adopteunelivraison.utils.AppPreference
import com.didaagency.adopteunelivraison.utils.AppUtils
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.utils.Constants
import com.didaagency.adopteunelivraison.view.baseViews.BaseActivity
import com.didaagency.adopteunelivraison.view.bottomSheets.ConfirmationBottomSheet
import com.didaagency.adopteunelivraison.view.fragments.accounts.ProfileFragment
import com.didaagency.adopteunelivraison.view.fragments.home_fragment.HomeFragment
import com.didaagency.adopteunelivraison.view.fragments.menu.MenuFragment
import com.didaagency.adopteunelivraison.view.fragments.merchant.MerhantFragment
import com.didaagency.adopteunelivraison.view.fragments.orders.OrdersFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


@AndroidEntryPoint
class MainActivity : BaseActivity(), CoroutineScope {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeFragment: HomeFragment
    private lateinit var profileFragment: ProfileFragment
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var job: Job
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        setUpObservers()
        setData()
        setClickListeners()
        homeFragment = HomeFragment()
        loadFragment(homeFragment)
    }


    private fun setUpObservers() {
        mainViewModel.clickEvent.observe(this) {
            when (it) {
                ClickEvents.BACK -> {
                    finish()
                }
                ClickEvents.HOME -> {
                }
                ClickEvents.OPEN_CLOSE_DRAWE -> {
                }
                ClickEvents.SYNC_DATA -> {
                }
            }
        }
    }



    private fun callExistBS() {
        var bottomSheet =
            ConfirmationBottomSheet(resources.getString(R.string.text_exit), resources.getString(R.string.text_exit_message))
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        bottomSheet.setOnClickListner(object : ConfirmationBottomSheet.OnItemClickListner {
            override fun onClick(isYesNo: Boolean) {
                if (isYesNo) {
                    finish()
                }
            }
        })
    }

    private fun showPermissionCompulsoryDialog() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (!AppUtils.isTimeAutomatic(this)) {
                showBlockerDialog(
                    this@MainActivity,
                    resources.getString(R.string.str_auto_time_not_enabled),
                    resources.getString(R.string.str_auto_time_explanation)
                )

            }
        } else {
            showBlockerDialog(
                this@MainActivity,
                resources.getString(R.string.str_auto_time_not_enabled),
                resources.getString(R.string.str_auto_time_explanation),
                { dialogInterface, i ->
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri: Uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                },
                null,
                false
            )
        }
    }

    private fun initViews() {
        job = Job()
        binding.bottomNavigation.setOnNavigationItemSelectedListener(bottomNavigation)
        binding.model = mainViewModel
    }

    fun enableLoc() {
        val manager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps()
        }
    }

    fun buildAlertMessageNoGps() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.text_alert))
        builder.setMessage(resources.getString(R.string.text_location_permission_detail))
            .setCancelable(false)
            .setPositiveButton(
                resources.getString(R.string.text_yes)
            ) { dialog: DialogInterface?, id: Int ->
                startActivity(
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                )
            }
            .setNegativeButton(resources.getString(R.string.text_no)) { dialog: DialogInterface, id: Int -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }

    override fun onStart() {
        super.onStart()
        showPermissionCompulsoryDialog()
        enableLoc()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }


    override fun onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack()
        } else {
            callExistBS()
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onDestroy() {
        job.cancel() // cancel the Job
        super.onDestroy()
    }

    private val bottomNavigation =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    if (homeFragment == null) {
                        homeFragment = HomeFragment()
                    }
                    loadFragment(homeFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_orders -> {
                    loadFragment(OrdersFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_menu -> {

                    loadFragment(MenuFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_merchant -> {

                    loadFragment(MerhantFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    profileFragment = ProfileFragment()
                    loadFragment(profileFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }


    fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment, fragment.tag)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    private fun setData() {
        var isOnline :Boolean = AppPreference.getSavedData(this, Constants.KEY_ONLINE_STATUS)
        if (isOnline){
            binding.swOnlineStatus.setText(resources.getString(R.string.text_online_status))
        } else {
            binding.swOnlineStatus.setText(resources.getString(R.string.text_offline_status))
        }
        binding.swOnlineStatus.isChecked = isOnline


    }

    private fun setClickListeners() {
        binding.swOnlineStatus.setOnCheckedChangeListener { _, isChecked ->
            // do whatever you need to do when the switch is toggled here
            AppPreference.saveData(this,isChecked,Constants.KEY_ONLINE_STATUS)
            if (isChecked) {
                binding.swOnlineStatus.setText(resources.getString(R.string.text_online_status))
            } else {
                binding.swOnlineStatus.setText(resources.getString(R.string.text_offline_status))
            }
        }
    }


}