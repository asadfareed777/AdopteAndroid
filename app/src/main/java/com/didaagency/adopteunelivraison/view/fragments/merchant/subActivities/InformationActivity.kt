package com.didaagency.adopteunelivraison.view.fragments.merchant.subActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.databinding.ActivityInformationBinding
import com.didaagency.adopteunelivraison.databinding.ActivityOrderDetailBinding
import com.didaagency.adopteunelivraison.utils.AppPreference
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.utils.Constants
import com.didaagency.adopteunelivraison.utils.IntentUtils
import com.didaagency.adopteunelivraison.view.baseViews.BaseActivity
import com.didaagency.adopteunelivraison.view.bottomSheets.ConfirmationBottomSheet
import com.didaagency.adopteunelivraison.view.fragments.merchant.MerchantViewModel
import com.didaagency.adopteunelivraison.view.fragments.orders.OrdersViewModel
import com.didaagency.adopteunelivraison.view.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InformationActivity : BaseActivity() {


    private lateinit var binding: ActivityInformationBinding
    private val viewModel: MerchantViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }
    private fun initViews() {
        binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.model = viewModel
        setUpObservers()
    }

    private fun setUpObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.clickEvent.observe(this@InformationActivity) {
                when (it) {
                    ClickEvents.BACK -> {
                        finish()
                    }
                    ClickEvents.SUBMIT -> {
                        callSubmitClick()
                    }

                    ClickEvents.INTERNET_CONNECTION -> {
                        showToast(resources.getString(R.string.internet_connection_message))
                    }

                    else -> {}
                }
            }
        }
    }

    private fun callSubmitClick() {

        if (isValidate()) {
            callConfirmBS()
        }
    }

    private fun isValidate(): Boolean {

        return true
    }

    private fun callConfirmBS() {
        var bottomSheet = ConfirmationBottomSheet(resources.getString(R.string.text_submit),resources.getString(R.string.text_submit_confirmation))
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        bottomSheet.setOnClickListner(object : ConfirmationBottomSheet.OnItemClickListner {
            override fun onClick(isYesNo: Boolean) {
                if (isYesNo) {
                     showToast("Api Under Process")
                }
            }
        })
    }
}