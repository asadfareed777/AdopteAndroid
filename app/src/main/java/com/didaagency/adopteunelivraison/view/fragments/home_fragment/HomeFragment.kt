package com.didaagency.adopteunelivraison.view.fragments.home_fragment

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.felix.horizontalbargraph.model.BarItem
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.data.network.request.orders.Customer
import com.didaagency.adopteunelivraison.data.network.request.orders.Earnings
import com.didaagency.adopteunelivraison.data.network.request.orders.Orders
import com.didaagency.adopteunelivraison.data.network.response.orders.OrderStatsResponse
import com.didaagency.adopteunelivraison.databinding.FragmentDashboardBinding
import com.didaagency.adopteunelivraison.databinding.ItemViewReviewsHorizontalBarLayoutBinding
import com.didaagency.adopteunelivraison.interfaces.RvItemClickListner
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.utils.Status
import com.didaagency.adopteunelivraison.view.adapters.EarningsHorizontalAdapter
import com.didaagency.adopteunelivraison.view.adapters.TabSelectItemAdapter
import com.didaagency.adopteunelivraison.view.adapters.TopCustomerAdapter
import com.didaagency.adopteunelivraison.view.adapters.orders.LastOrderAdapter
import com.didaagency.adopteunelivraison.view.baseViews.BaseFragment
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        setClickListeners()
        setData()
    }

    private fun setData() {
        binding.model = viewModel
        setTabsData()
        setEarningsData()
        setTotalOrdersData()
        setTopCustomerData()
        setSalesOverviewBarChart()
        setOverviewOfReviewsBarChart(75,5)
        setOverviewOfReviewsBarChart(63,4)
        setOverviewOfReviewsBarChart(44,2)
        setOverviewOfReviewsBarChart(89,5)
        binding.tvEarnings.setText("$123441223.3")
    }

    private fun setOverviewOfReviewsBarChart( percent:Int, stars :Int) {
        var localBinding = ItemViewReviewsHorizontalBarLayoutBinding.inflate(layoutInflater)
        localBinding.tvPercentage.setText(percent.toString()+"%")
        localBinding.tvStars.setText(stars.toString()+" Stars")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            localBinding.progressBar.setProgress(percent,true)
        }else{
            localBinding.progressBar.setProgress(percent)
        }
        binding.horizontalBarChart.addView(localBinding.root)
    }

    private fun setSalesOverviewBarChart() {
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(8f, 0))
        entries.add(BarEntry(2f, 1))
        entries.add(BarEntry(5f, 2))
        entries.add(BarEntry(20f, 3))
        entries.add(BarEntry(15f, 4))
        entries.add(BarEntry(19f, 5))
        entries.add(BarEntry(8f, 6))
        entries.add(BarEntry(2f, 7))
        entries.add(BarEntry(5f, 8))
        entries.add(BarEntry(20f, 9))
        entries.add(BarEntry(15f, 10))
        entries.add(BarEntry(19f, 11))

        val bardataset = BarDataSet(entries, "Sales")

        val labels = ArrayList<String>()
        labels.add("Dec")
        labels.add("Nov")
        labels.add("Oct")
        labels.add("Sep")
        labels.add("Aug")
        labels.add("Jul")
        labels.add("Jun")
        labels.add("May")
        labels.add("Apr")
        labels.add("Mar")
        labels.add("Feb")
        labels.add("Jan")

        val data = BarData(labels, bardataset)
        binding.barChart.setData(data) // set the data and list of lables into chart
        binding.barChart.setDescription(" ") // set the description
        bardataset.setColor(resources.getColor(R.color.appColor))
        binding.barChart.animateY(5000)

    }


    private fun setTabsData() {
        var tabArrays = arrayOf<String>("All", "Processing", "Ready", "Completed")
        binding.rvTabs.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.HORIZONTAL,false)
        var tabSelectItemAdapter = TabSelectItemAdapter(tabArrays.toList(),requireContext())
        binding.rvTabs.adapter = tabSelectItemAdapter
        tabSelectItemAdapter.onTabItemClickListner = object : RvItemClickListner<String> {
            override fun onItemCLick(clickEvent: ClickEvents?, item: String?, position: Int) {
                when(clickEvent){
                    ClickEvents.RV_PARENT_CLICK -> {
                        when(item){
                            "All","Processing" ,"Ready","Completed"->{
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
                                setLastOrdersData(orderList)
                            }

                        }
                    }
                }
            }
        }
        tabSelectItemAdapter.setDefaultClick()
    }
    private fun setEarningsData() {
        binding.rvEarnings.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.HORIZONTAL,false)

        var list = ArrayList<Earnings>()
        list.add(Earnings("Sales this week","$100023","#4AC3A2"))
        list.add(Earnings("Earning this week","$100023","#9789E8"))
        list.add(Earnings("Your Earning","$100023","#FAB54D"))
        var adapter = EarningsHorizontalAdapter(list.toList(),requireContext())
        binding.rvEarnings.adapter = adapter
        adapter.onTabItemClickListner = object : RvItemClickListner<Earnings> {
            override fun onItemCLick(clickEvent: ClickEvents?, item: Earnings?, position: Int) {
                when(clickEvent){
                    ClickEvents.RV_PARENT_CLICK -> {
//                        showToast(item.toString())
                    }
                }
            }
        }
    }


    private fun setLastOrdersData(orderList: ArrayList<Orders>) {
        binding.rvLastOrders.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL, false
        )
        var adapter = LastOrderAdapter(orderList.toList(), requireContext())
        binding.rvLastOrders.adapter = adapter
        adapter.onItemClickListner = object : RvItemClickListner<Orders> {
            override fun onItemCLick(clickEvent: ClickEvents?, item: Orders?, position: Int) {
                when (clickEvent) {
                    ClickEvents.RV_PARENT_CLICK -> {
//                        callAcceptOrderBS(item)
                    }
                    else -> {}
                }
            }
        }
        if (adapter.itemCount>0){
            showNoDataLastOrder(false)
        }else{
            showNoDataLastOrder(true)
        }
    }
    fun showNoDataLastOrder(show: Boolean) {
        binding.rvLastOrders.visibility = if (show) View.GONE else View.VISIBLE
        binding.tvNoRecordFoundLastOrder.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun setTopCustomerData() {
        var customers = ArrayList<Customer>()
        var image = "https://upload.wikimedia.org/wikipedia/commons/2/2e/Ice_cream_with_whipped_cream%2C_chocolate_syrup%2C_and_a_wafer_%28cropped%29.jpg"
        customers.add(Customer("Basit Baloch","233 Orders",""+image,"Member Since Sat, Jan 19 2022 11:00 am"))
        customers.add(Customer("Naeem","411 Orders",""+image,"Member Since Sat, Jan 19 2022 11:00 am"))
        customers.add(Customer("Asad","633 Orders",""+image,"Member Since Sat, Jan 19 2022 11:00 am"))
        binding.rvTopCustomer.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL, false
        )
        var adapter = TopCustomerAdapter(customers.toList(), requireContext())
        binding.rvTopCustomer.adapter = adapter
        adapter.onItemClickListner = object : RvItemClickListner<Customer> {
            override fun onItemCLick(clickEvent: ClickEvents?, item: Customer?, position: Int) {
                when (clickEvent) {
                    ClickEvents.RV_PARENT_CLICK -> {
//                        callAcceptOrderBS(item)
                    }
                    else -> {}
                }
            }
        }
        if (adapter.itemCount>0){
            showNoDataTopCustomer(false)
        }else{
            showNoDataTopCustomer(true)
        }
    }
    fun showNoDataTopCustomer(show: Boolean) {
        binding.rvTopCustomer.visibility = if (show) View.GONE else View.VISIBLE
        binding.tvNoRecordFoundTopCustomer.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun setTotalOrdersData() {
        binding.rvTotalOrders.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.HORIZONTAL,false)

        var list = ArrayList<Earnings>()
        list.add(Earnings("Total Orders","$100023","#C4B5D4"))
        list.add(Earnings("Total Cancel","$100023","#E99A9F"))
        list.add(Earnings("Total Receive","$100023","#45ADCA"))
        var adapter = EarningsHorizontalAdapter(list.toList(),requireContext())
        binding.rvTotalOrders.adapter = adapter
        adapter.onTabItemClickListner = object : RvItemClickListner<Earnings> {
            override fun onItemCLick(clickEvent: ClickEvents?, item: Earnings?, position: Int) {
                when(clickEvent){
                    ClickEvents.RV_PARENT_CLICK -> {
//                        showToast(item.toString())
                    }
                }
            }
        }
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
                    ClickEvents.SEARCH_ICON_CLICK -> {
                        showToast(resources.getString(R.string.text_search))
                    }
                    ClickEvents.NOTIFICATION -> {
                        showToast(resources.getString(R.string.text_notification))
                    }
                }
            }
            viewModel.orderStatsResponse.observe(requireActivity()) {
                when (it.status) {
                    Status.SUCCESS -> {
                        setStatsData(it.data!!)
                    }
                    Status.ERROR -> {
                        hideLoadingDialog()
                        showToast(it.message.toString())
                    }
                    Status.LOADING -> {
                        showLoadingDialog()
                    }
                }
            }
        }


    }

    private fun setStatsData(data: OrderStatsResponse) {

    }
    private fun setClickListeners() {

    }

    override fun onDestroy() {
        super.onDestroy()
    }

}