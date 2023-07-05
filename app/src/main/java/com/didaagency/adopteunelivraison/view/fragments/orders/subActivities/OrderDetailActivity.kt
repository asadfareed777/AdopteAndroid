package com.didaagency.adopteunelivraison.view.fragments.orders.subActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.data.network.request.orders.OrderItems
import com.didaagency.adopteunelivraison.data.network.request.orders.Orders
import com.didaagency.adopteunelivraison.databinding.ActivityOrderDetailBinding
import com.didaagency.adopteunelivraison.databinding.ActivitySignUpBinding
import com.didaagency.adopteunelivraison.databinding.FragmentOrdersBinding
import com.didaagency.adopteunelivraison.interfaces.RvItemClickListner
import com.didaagency.adopteunelivraison.utils.AppUtils
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.utils.IntentUtils
import com.didaagency.adopteunelivraison.utils.Status
import com.didaagency.adopteunelivraison.view.adapters.orders.OrderAdapter
import com.didaagency.adopteunelivraison.view.adapters.orders.OrderItemAdapter
import com.didaagency.adopteunelivraison.view.baseViews.BaseActivity
import com.didaagency.adopteunelivraison.view.dialogs.AcceptOrderDialog
import com.didaagency.adopteunelivraison.view.dialogs.RejectOrderDialog
import com.didaagency.adopteunelivraison.view.fragments.orders.OrdersViewModel
import com.didaagency.adopteunelivraison.view.login.LoginActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailActivity : BaseActivity() {


    private lateinit var binding: ActivityOrderDetailBinding
    private val viewModel: OrdersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        getExtrasFromIntent()
    }

    private fun getExtrasFromIntent() {
        if (intent != null) {
            if (this.intent.extras != null) {
                val item = this.intent.extras!!.getString("item")
                var orderItem = Gson().fromJson(item, Orders::class.java)


                var orderList = ArrayList<OrderItems>()
                var order3 = OrderItems()
                order3.title = "Ice Cream"
                order3.price = "$322"
                order3.description = "4 Item for Ice Cream"
                order3.image = "https://upload.wikimedia.org/wikipedia/commons/2/2e/Ice_cream_with_whipped_cream%2C_chocolate_syrup%2C_and_a_wafer_%28cropped%29.jpg"

                orderList.add(order3)
                orderList.add(order3)
                orderList.add(order3)

                setOrderItemsData(orderList)
            }
        }
    }

    private fun initViews() {
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.model = viewModel
        setUpObservers()
    }

    private fun setUpObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.clickEvent.observe(this@OrderDetailActivity) {
                when (it) {
                    ClickEvents.BACK -> {
                        finish()
                    }
                    ClickEvents.ACCEPTED -> {
                        callAcceptOrderDialog()
                    }
                    ClickEvents.REJECTED -> {
                        callRejectOrderDialog()
                    }
                    ClickEvents.INTERNET_CONNECTION -> {
                        showToast(resources.getString(R.string.internet_connection_message))
                    }
                    else -> {}
                }
            }
        }
    }

    private fun callAcceptOrderDialog() {
        var dialog = AcceptOrderDialog(this,resources.getString(R.string.text_accept_order_detail))
        dialog.show()
        dialog.setOnClickListner(object :AcceptOrderDialog.OnDialogItemClickListner{
            override fun onClick(isYesNo: Boolean) {
                if (isYesNo){
                    showToast("Accept Order Api in Process")
                }
            }
        })
    }


    private fun setOrderItemsData(orderList: ArrayList<OrderItems>) {
        binding.rvOrderItems.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL, false
        )
        var adapter = OrderItemAdapter(orderList.toList(), this)
        binding.rvOrderItems.adapter = adapter
        adapter.onItemClickListner = object : RvItemClickListner<OrderItems> {
            override fun onItemCLick(clickEvent: ClickEvents?, item: OrderItems?, position: Int) {
                when (clickEvent) {
                    ClickEvents.RV_PARENT_CLICK -> {
                        var bundle = Bundle()
                        bundle.putString("item", Gson().toJson(item))
                        //     IntentUtils.callIntentWithFlagsBundle(this,OrderDetailActivity::class.java,false,bundle)
                    }

                    else -> {}
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
        binding.rvOrderItems.visibility = if (show) View.GONE else View.VISIBLE
        binding.tvNoItemsFound.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun callRejectOrderDialog() {
        var dialog = RejectOrderDialog(this,resources.getString(R.string.text_accept_order_detail))
        dialog.show()
        dialog.setOnClickListner(object : RejectOrderDialog.OnDialogItemClickListner{
            override fun onClick(isYesNo: Boolean) {
                if (isYesNo){
                    showToast("Reject Order Api in Process")
                }
            }
        })
    }
}