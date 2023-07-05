package com.didaagency.adopteunelivraison.view.fragments.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.data.network.request.orders.Orders
import com.didaagency.adopteunelivraison.databinding.FragmentOrdersBinding
import com.didaagency.adopteunelivraison.interfaces.RvItemClickListner
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.utils.IntentUtils
import com.didaagency.adopteunelivraison.view.adapters.TabSelectItemAdapter
import com.didaagency.adopteunelivraison.view.adapters.orders.OrderAdapter
import com.didaagency.adopteunelivraison.view.baseViews.BaseFragment
import com.didaagency.adopteunelivraison.view.bottomSheets.AcceptOrderBottomSheet
import com.didaagency.adopteunelivraison.view.dialogs.AcceptOrderDialog
import com.didaagency.adopteunelivraison.view.dialogs.RejectOrderDialog
import com.didaagency.adopteunelivraison.view.fragments.orders.subActivities.OrderDetailActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : BaseFragment() {

    private lateinit var binding: FragmentOrdersBinding
    private val viewModel: OrdersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrdersBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setData()
    }

    private fun setData() {
        setTabsData()
        populateNewOrder()
    }

    private fun setTabsData() {
        var tabArrays = arrayOf<String>("New Orders", "Order Processing", "Order Ready")
        binding.rvTabs.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL, false
        )
        var tabSelectItemAdapter = TabSelectItemAdapter(tabArrays.toList(), requireContext())
        binding.rvTabs.adapter = tabSelectItemAdapter
        tabSelectItemAdapter.onTabItemClickListner = object : RvItemClickListner<String> {
            override fun onItemCLick(clickEvent: ClickEvents?, item: String?, position: Int) {
                when (clickEvent) {
                    ClickEvents.RV_PARENT_CLICK -> {
                        when(item?.trim()){
                            "New Orders"->{
                                populateNewOrder()
                            }
                            "Order Processing"->{
                                populateAcceptedOrder()
                            }
                            "Order Ready"->{
                                populateReadyOrder()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun populateNewOrder() {
        var orderList = ArrayList<Orders>()
        var order = Orders()
        order.orderStatus = "New"
        order.deliveryStatus = "Deliver"
        order.paymentStatus = "Paid"
        order.date = "Yesterday"
        order.description = "3 Item for DEdi"
        order.orderId = 1233
        order.price = "$323"
        order.title = "3 Briyani"
        order.image = "https://upload.wikimedia.org/wikipedia/commons/2/2e/Ice_cream_with_whipped_cream%2C_chocolate_syrup%2C_and_a_wafer_%28cropped%29.jpg"

        orderList.add(order)
        orderList.add(order)
        orderList.add(order)
        orderList.add(order)
        orderList.add(order)
        setOrdersData(orderList)
    }

    private fun populateAcceptedOrder() {

        var orderList = ArrayList<Orders>()
        var order2 = Orders()
        order2.orderStatus = "Accepted"
        order2.deliveryStatus = "Pickup"
        order2.paymentStatus = "UnPaid"
        order2.date = "3 months ago"
        order2.price = "$323"
        order2.title = "3 Briyani"
        order2.image = "https://upload.wikimedia.org/wikipedia/commons/2/2e/Ice_cream_with_whipped_cream%2C_chocolate_syrup%2C_and_a_wafer_%28cropped%29.jpg"
        order2.description = "3 Item for Briyani"
        order2.orderId = 1233

        orderList.add(order2)
        orderList.add(order2)
        orderList.add(order2)
        setOrdersData(orderList)

    }
    private fun populateReadyOrder() {

        var orderList = ArrayList<Orders>()
        var order3 = Orders()
        order3.orderStatus = "Ready For Pickup"
        order3.deliveryStatus = "Pickup"
        order3.paymentStatus = "UnPaid"
        order3.date = "3 months ago"
        order3.description = "4 Item for Pizza"
        order3.orderId = 1233
        order3.price = "$323"
        order3.title = "3 Briyani"
        order3.image = "https://upload.wikimedia.org/wikipedia/commons/2/2e/Ice_cream_with_whipped_cream%2C_chocolate_syrup%2C_and_a_wafer_%28cropped%29.jpg"
        orderList.add(order3)
        orderList.add(order3)
        orderList.add(order3)
        setOrdersData(orderList)
    }

    private fun setOrdersData(orderList: ArrayList<Orders>) {
        binding.rvOrders.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL, false
        )
        var adapter = OrderAdapter(orderList.toList(), requireContext())
        binding.rvOrders.adapter = adapter
        adapter.onItemClickListner = object : RvItemClickListner<Orders> {
            override fun onItemCLick(clickEvent: ClickEvents?, item: Orders?, position: Int) {
                when (clickEvent) {
                    ClickEvents.RV_PARENT_CLICK -> {
                        callAcceptOrderBS(item)
                    }
                    else -> {}
                }
            }
        }
        if (adapter.itemCount>0){
            showNoData(false)
        }else{
            showNoData(true)
        }
    }

    private fun callAcceptOrderBS(item: Orders?) {
        val bottomSheet = AcceptOrderBottomSheet(item!!)
        bottomSheet.show(childFragmentManager,bottomSheet.tag)
        bottomSheet.setOnClickListner(object : AcceptOrderBottomSheet.OnItemClickListner{
            override fun onClick(event: ClickEvents) {
                when(event){
                    ClickEvents.ORDER_DETAIL ->{
                        var bundle = Bundle()
                        bundle.putString("item",Gson().toJson(item))
                        IntentUtils.callIntentWithFlagsBundle(requireContext(),OrderDetailActivity::class.java,false,bundle)
                    }
                    ClickEvents.ACCEPTED ->{
                        callAcceptOrderDialog()
                    }
                    ClickEvents.REJECTED ->{
                        callRejectOrderDialog()
                    }
                }
            }

        })
    }
    private fun callRejectOrderDialog() {
        var dialog = RejectOrderDialog(requireContext(),resources.getString(R.string.text_accept_order_detail))
        dialog.show()
        dialog.setOnClickListner(object : RejectOrderDialog.OnDialogItemClickListner{
            override fun onClick(isYesNo: Boolean) {
                if (isYesNo){
                    showToast("Reject Order Api in Process")
                }
            }
        })
    }
    private fun callAcceptOrderDialog() {
        var dialog = AcceptOrderDialog(requireContext(),resources.getString(R.string.text_accept_order_detail))
        dialog.show()
        dialog.setOnClickListner(object : AcceptOrderDialog.OnDialogItemClickListner{
            override fun onClick(isYesNo: Boolean) {
                if (isYesNo){
                    showToast("Accept Order Api in Process")
                }
            }
        })
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
                }
            }

        }
    }
    fun showNoData(show: Boolean) {
        binding.rvOrders.visibility = if (show) View.GONE else View.VISIBLE
        binding.tvNoRecordFound.visibility = if (show) View.VISIBLE else View.GONE
    }

}