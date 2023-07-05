package com.didaagency.adopteunelivraison.view.fragments.merchant.subActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.data.network.request.merchant.MerchantMenu
import com.didaagency.adopteunelivraison.data.network.response.merchant.StoreHour
import com.didaagency.adopteunelivraison.databinding.ActivitySettingsBinding
import com.didaagency.adopteunelivraison.databinding.ActivityStoreHourBinding
import com.didaagency.adopteunelivraison.interfaces.RvItemClickListner
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.utils.IntentUtils
import com.didaagency.adopteunelivraison.view.adapters.merchant.MerchantMenuGridAdapter
import com.didaagency.adopteunelivraison.view.adapters.merchant.StoreHourAdapter
import com.didaagency.adopteunelivraison.view.baseViews.BaseActivity
import com.didaagency.adopteunelivraison.view.bottomSheets.ConfirmationBottomSheet
import com.didaagency.adopteunelivraison.view.fragments.merchant.MerchantViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreHourActivity : BaseActivity() {

    private lateinit var binding: ActivityStoreHourBinding
    private val viewModel: MerchantViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        setStoreHourData()
    }

    private fun initViews() {
        binding = ActivityStoreHourBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.model = viewModel
        setUpObservers()
    }

    private fun setUpObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.clickEvent.observe(this@StoreHourActivity) {
                when (it) {
                    ClickEvents.BACK -> {
                        finish()
                    }
                    ClickEvents.EDIT,ClickEvents.ADD -> {
                        IntentUtils.callIntentWithFlags(this@StoreHourActivity,AddStoreHoursActivity::class.java,false)
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


    private fun setStoreHourData() {
        binding.rvStoreHours.layoutManager = GridLayoutManager(this, 2)

        var list = ArrayList<StoreHour>()
        list.add(StoreHour(true, "Monday", "1:00 am", "11:55 pm"))
        list.add(StoreHour(true, "Tuesday", "1:00 am", "11:55 pm"))
        list.add(StoreHour(true, "Wednesday", "1:00 am", "11:55 pm"))
        list.add(StoreHour(true, "Thrusday", "1:00 am", "11:55 pm"))
        list.add(StoreHour(true, "Friday", "1:00 am", "11:55 pm"))
        list.add(StoreHour(true, "Saturday", "1:00 am", "11:55 pm"))
        list.add(StoreHour(true, "Sunday", "1:00 am", "11:55 pm"))
        var adapter = StoreHourAdapter(list.toList(), this)
        binding.rvStoreHours.adapter = adapter
        adapter.onTabItemClickListner = object : RvItemClickListner<StoreHour> {
            override fun onItemCLick(clickEvent: ClickEvents?, item: StoreHour?, position: Int) {
                when (clickEvent) {
                    ClickEvents.RV_PARENT_CLICK -> {

                    }
                }
            }
        }
        if (adapter.itemCount > 0) {
            showNoData(false)
        } else {
            showNoData(true)
        }
    }

    fun showNoData(show: Boolean) {
        binding.rvStoreHours.visibility = if (show) View.GONE else View.VISIBLE
        binding.tvNoRecordFound.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun callConfirmBS() {
        var bottomSheet = ConfirmationBottomSheet(
            resources.getString(R.string.text_submit),
            resources.getString(R.string.text_submit_confirmation)
        )
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