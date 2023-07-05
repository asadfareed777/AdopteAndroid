package com.didaagency.adopteunelivraison.view.adapters.orders

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.data.network.request.orders.Orders
import com.didaagency.adopteunelivraison.databinding.ItemViewLastOrdersLayoutBinding
import com.didaagency.adopteunelivraison.databinding.ItemViewMerchantMenuGridLayoutBinding
import com.didaagency.adopteunelivraison.databinding.ItemViewOrdersLayoutBinding
import com.didaagency.adopteunelivraison.databinding.ItemViewTabSelectionLayoutBinding
import com.didaagency.adopteunelivraison.interfaces.RvItemClickListner
import com.didaagency.adopteunelivraison.utils.AppUtils
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.view.baseViews.BaseRecyclerViewHolder


class LastOrderAdapter(val arrListData: List<Orders>?, private val mContext: Context) :
    RecyclerView.Adapter<LastOrderAdapter.MyViewHolder>() {

    var onItemClickListner: RvItemClickListner<Orders>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemViewLastOrdersLayoutBinding = ItemViewLastOrdersLayoutBinding.inflate(
            LayoutInflater.from(
                mContext
            ), parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {

        holder.updateView(arrListData!![position])
        holder.binding.llParent.setOnClickListener {
            onItemClickListner!!.onItemCLick(ClickEvents.RV_PARENT_CLICK,arrListData[position], position)
        }
    }

    override fun getItemCount(): Int {
        return if (arrListData == null) 0 else arrListData!!.size
    }


    inner class MyViewHolder(itemView: ViewBinding) :
        BaseRecyclerViewHolder(itemView.root) {
        val binding: ItemViewLastOrdersLayoutBinding
        init {
            binding = itemView as ItemViewLastOrdersLayoutBinding
        }

        fun updateView(data: Orders) {
            binding.tvOrderId.setText(("Order # "+data.orderId?.toString()))
            binding.tvDate.setText((data.date?.trim { it <= ' ' }))
            binding.tvOrderDescription.setText((data.description?.trim { it <= ' ' }))
            binding.tvOrderStatus.setText((data.orderStatus?.trim { it <= ' ' }))
            binding.tvPaymentStatus.setText((data.paymentStatus?.trim { it <= ' ' }))
            binding.tvDeliveryStatus.setText((data.deliveryStatus?.trim { it <= ' ' }))
        }
    }

}


