package com.didaagency.adopteunelivraison.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.data.network.request.orders.Customer
import com.didaagency.adopteunelivraison.data.network.request.orders.Orders
import com.didaagency.adopteunelivraison.databinding.ItemViewLastOrdersLayoutBinding
import com.didaagency.adopteunelivraison.databinding.ItemViewMerchantMenuGridLayoutBinding
import com.didaagency.adopteunelivraison.databinding.ItemViewOrdersLayoutBinding
import com.didaagency.adopteunelivraison.databinding.ItemViewTabSelectionLayoutBinding
import com.didaagency.adopteunelivraison.databinding.ItemViewTopCustomerLayoutBinding
import com.didaagency.adopteunelivraison.interfaces.RvItemClickListner
import com.didaagency.adopteunelivraison.utils.AppUtils
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.view.baseViews.BaseRecyclerViewHolder


class TopCustomerAdapter(val arrListData: List<Customer>?, private val mContext: Context) :
    RecyclerView.Adapter<TopCustomerAdapter.MyViewHolder>() {

    var onItemClickListner: RvItemClickListner<Customer>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemViewTopCustomerLayoutBinding = ItemViewTopCustomerLayoutBinding.inflate(
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
        val binding: ItemViewTopCustomerLayoutBinding
        init {
            binding = itemView as ItemViewTopCustomerLayoutBinding
        }

        fun updateView(data: Customer) {
            binding.tvDate.setText((data.date?.toString()))
            binding.tvName.setText((data.name?.trim { it <= ' ' }))
            binding.tvOrderComplete.setText((data.numberOfOrderComplete?.trim { it <= ' ' }))
            Glide
                .with(mContext)
                .load(data.image)
                .centerCrop()
                .placeholder(R.drawable.ic_profile)
                .into(binding.ivCustomer)
        }
    }

}


