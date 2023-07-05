package com.didaagency.adopteunelivraison.view.fragments.merchant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.data.network.request.merchant.MerchantMenu
import com.didaagency.adopteunelivraison.data.network.response.merchant.PayoutHistory
import com.didaagency.adopteunelivraison.data.network.response.merchant.TransactionHistory
import com.didaagency.adopteunelivraison.databinding.FragmentMerhantBinding
import com.didaagency.adopteunelivraison.interfaces.RvItemClickListner
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.utils.IntentUtils
import com.didaagency.adopteunelivraison.view.adapters.merchant.MerchantMenuGridAdapter
import com.didaagency.adopteunelivraison.view.adapters.TabSelectItemAdapter
import com.didaagency.adopteunelivraison.view.adapters.merchant.PayoutHistoryAdapter
import com.didaagency.adopteunelivraison.view.adapters.merchant.TransactionHistoryAdapter
import com.didaagency.adopteunelivraison.view.baseViews.BaseFragment
import com.didaagency.adopteunelivraison.view.bottomSheets.AcceptOrderBottomSheet
import com.didaagency.adopteunelivraison.view.bottomSheets.RequestPayoutBottomSheet
import com.didaagency.adopteunelivraison.view.bottomSheets.SetPayoutBottomSheet
import com.didaagency.adopteunelivraison.view.fragments.merchant.subActivities.AddressActivity
import com.didaagency.adopteunelivraison.view.fragments.merchant.subActivities.InformationActivity
import com.didaagency.adopteunelivraison.view.fragments.merchant.subActivities.SettingsActivity
import com.didaagency.adopteunelivraison.view.fragments.merchant.subActivities.StoreHourActivity
import com.didaagency.adopteunelivraison.view.fragments.orders.subActivities.OrderDetailActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MerhantFragment : BaseFragment() {

    private lateinit var binding: FragmentMerhantBinding
    private val viewModel: MerchantViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMerhantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setData()
    }

    private fun setData() {
        binding.tvEarnings.setText("$12312313")
        setEarningsData()
        setMerchantHistoryData()
        setTabData()
    }

    private fun setTabData() {
        var tabArrays = arrayOf<String>("Transaction History", "Payout History")
        binding.rvTabs.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        var tabSelectItemAdapter = TabSelectItemAdapter(tabArrays.toList(), requireContext())
        binding.rvTabs.adapter = tabSelectItemAdapter
        tabSelectItemAdapter.onTabItemClickListner = object : RvItemClickListner<String> {
            override fun onItemCLick(clickEvent: ClickEvents?, item: String?, position: Int) {
                when (clickEvent) {
                    ClickEvents.RV_PARENT_CLICK -> {
                        if (item.toString().equals("Transaction History", true)) {
                            setMerchantHistoryData()
                        } else if (item.toString().equals("Payout History", true)) {
                            setPayoutHistoryData()
                        }
                    }
                }
            }
        }
    }

    private fun setEarningsData() {
        binding.rvMerchantMenus.layoutManager = GridLayoutManager(requireContext(), 3)

        var list = ArrayList<MerchantMenu>()
        list.add(MerchantMenu(R.drawable.img_home, "Information"))
        list.add(MerchantMenu(R.drawable.ic_location, "Address"))
        list.add(MerchantMenu(R.drawable.ic_settings, "Settings"))
        list.add(MerchantMenu(R.drawable.ic_time, "Store Hour"))
        list.add(MerchantMenu(R.drawable.ic_payout, "Set Payout"))
        var adapter = MerchantMenuGridAdapter(list.toList(), requireContext())
        binding.rvMerchantMenus.adapter = adapter
        adapter.onTabItemClickListner = object : RvItemClickListner<MerchantMenu> {
            override fun onItemCLick(clickEvent: ClickEvents?, item: MerchantMenu?, position: Int) {
                when (clickEvent) {
                    ClickEvents.RV_PARENT_CLICK -> {
                        when (item?.title) {
                            "Information"->{
                                IntentUtils.callIntentWithFlags(requireContext(),InformationActivity::class.java,false)
                            }
                            "Address"->{
                                IntentUtils.callIntentWithFlags(requireContext(),AddressActivity::class.java,false)
                            }
                            "Settings"->{
                                IntentUtils.callIntentWithFlags(requireContext(), SettingsActivity::class.java,false)
                            }
                            "Store Hour"->{
                                IntentUtils.callIntentWithFlags(requireContext(),StoreHourActivity::class.java,false)
                            }
                            "Set Payout"->{
                                callSetPayoutBS()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun callSetPayoutBS() {
        val bottomSheet = SetPayoutBottomSheet()
        bottomSheet.show(childFragmentManager,bottomSheet.tag)
        bottomSheet.setOnClickListner(object : SetPayoutBottomSheet.OnItemClickListner{
            override fun onClick(event: ClickEvents) {
                when(event){
                    ClickEvents.SUBMIT ->{
                        showToast("Api Under Process")
                    }
                }
            }

        })
    }

    private fun setMerchantHistoryData() {
        binding.rvMerchantHistory.layoutManager = LinearLayoutManager(requireContext())

        var list = ArrayList<TransactionHistory>()
        list.add(TransactionHistory("$123124455.3", "Payment to Order # 1221", "Wed Feb, 14 2022", "$231.2"))
        list.add(TransactionHistory("$123124455.3", "Payment to Order # 121", "Wed Feb, 14 2022", "$231.2"))
        list.add(TransactionHistory("$123124455.3", "Payment to Order # 321", "Wed Feb, 14 2022", "$231.2"))
        list.add(TransactionHistory("$123124455.3", "Payment to Order # 12321", "Wed Feb, 14 2022", "$231.2"))
        list.add(TransactionHistory("$123124455.3", "Payment to Order # 13", "Wed Feb, 14 2022", "$231.2"))

        var adapter = TransactionHistoryAdapter(list.toList(), requireContext())
        binding.rvMerchantHistory.adapter = adapter
        adapter.onTabItemClickListner = object : RvItemClickListner<TransactionHistory> {
            override fun onItemCLick(
                clickEvent: ClickEvents?,
                item: TransactionHistory?,
                position: Int
            ) {
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

    private fun setPayoutHistoryData() {
        binding.rvMerchantHistory.layoutManager = LinearLayoutManager(requireContext())

        var list = ArrayList<PayoutHistory>()
        list.add(PayoutHistory("$125.3", "Paid", "Wed Feb, 14 2022", "Payout to naa@gmail.com"))
        list.add(PayoutHistory("$125.3", "Paid", "Wed Feb, 14 2022", "Payout to naa@gmail.com"))
        list.add(PayoutHistory("$125.3", "Paid", "Wed Feb, 14 2022", "Payout to naa@gmail.com"))
        list.add(PayoutHistory("$125.3", "Paid", "Wed Feb, 14 2022", "Payout to naa@gmail.com"))
        list.add(PayoutHistory("$125.3", "Paid", "Wed Feb, 14 2022", "Payout to naa@gmail.com"))

        var adapter = PayoutHistoryAdapter(list.toList(), requireContext())
        binding.rvMerchantHistory.adapter = adapter
        adapter.onTabItemClickListner = object : RvItemClickListner<PayoutHistory> {
            override fun onItemCLick(
                clickEvent: ClickEvents?,
                item: PayoutHistory?,
                position: Int
            ) {
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
        binding.rvMerchantHistory.visibility = if (show) View.GONE else View.VISIBLE
        binding.tvNoRecordFound.visibility = if (show) View.VISIBLE else View.GONE
    }
    private fun initViews() {
        binding.model = viewModel
        setUpObservers()
    }

    private fun setUpObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.clickEvent.observe(requireActivity()) {
                when (it) {
                    ClickEvents.BACK -> {
                        activity?.finish()
                    }
                    ClickEvents.INTERNET_CONNECTION -> {
                        showToast(resources.getString(R.string.internet_connection_message))
                    }
                    ClickEvents.REQUEST_PAYOUT -> {
                       callRequestPayoutBS()
                    }
                }
            }
        }
    }

    private fun callRequestPayoutBS() {
        val bottomSheet = RequestPayoutBottomSheet()
        bottomSheet.show(childFragmentManager,bottomSheet.tag)
        bottomSheet.setOnClickListner(object : RequestPayoutBottomSheet.OnItemClickListner{
            override fun onClick(event: ClickEvents) {
                when(event){
                    ClickEvents.SUBMIT ->{
                        showToast("Api Under Process")
                    }
                }
            }

        })
    }

}