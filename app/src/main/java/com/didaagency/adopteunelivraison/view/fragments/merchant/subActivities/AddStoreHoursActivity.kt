package com.didaagency.adopteunelivraison.view.fragments.merchant.subActivities

import android.icu.lang.UCharacter.VerticalOrientation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.data.network.response.merchant.StoreHour
import com.didaagency.adopteunelivraison.databinding.ActivityAddStoreHoursBinding
import com.didaagency.adopteunelivraison.databinding.ActivityStoreHourBinding
import com.didaagency.adopteunelivraison.interfaces.RvItemClickListner
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.view.adapters.TabSelectItemAdapter
import com.didaagency.adopteunelivraison.view.adapters.merchant.DayItemAdapter
import com.didaagency.adopteunelivraison.view.adapters.merchant.StoreHourAdapter
import com.didaagency.adopteunelivraison.view.baseViews.BaseActivity
import com.didaagency.adopteunelivraison.view.bottomSheets.ConfirmationBottomSheet
import com.didaagency.adopteunelivraison.view.fragments.merchant.MerchantViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddStoreHoursActivity : BaseActivity() {


    private lateinit var binding: ActivityAddStoreHoursBinding
    private val viewModel: MerchantViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        setStoreHourData()
    }

    private fun initViews() {
        binding = ActivityAddStoreHoursBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.model = viewModel
        setUpObservers()
    }

    private fun setUpObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.clickEvent.observe(this@AddStoreHoursActivity) {
                when (it) {
                    ClickEvents.BACK -> {
                        finish()
                    }
                    ClickEvents.EDIT -> {

                    }
                    ClickEvents.ADD -> {
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

    private fun setStoreHourData() {
        var tabArrays = arrayOf<String>("Monday", "Tuesday", "Wednesday", "Thrusday", "Friday", "Saturday", "Sunday")
        binding.rvStoreHours.layoutManager = GridLayoutManager(this,2)
        var adapter = DayItemAdapter(tabArrays.toList(),this)
        binding.rvStoreHours.adapter = adapter
        adapter.onTabItemClickListner = object : RvItemClickListner<String> {
            override fun onItemCLick(clickEvent: ClickEvents?, item: String?, position: Int) {
                when(clickEvent){
                    ClickEvents.RV_PARENT_CLICK -> {
                        showToast(item.toString())
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