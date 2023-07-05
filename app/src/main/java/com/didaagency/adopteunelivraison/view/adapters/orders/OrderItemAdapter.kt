package com.didaagency.adopteunelivraison.view.adapters.orders

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.data.network.request.orders.OrderItems
import com.didaagency.adopteunelivraison.data.network.request.orders.Orders
import com.didaagency.adopteunelivraison.databinding.ItemViewMerchantMenuGridLayoutBinding
import com.didaagency.adopteunelivraison.databinding.ItemViewOrderDetailLayoutBinding
import com.didaagency.adopteunelivraison.databinding.ItemViewOrdersLayoutBinding
import com.didaagency.adopteunelivraison.databinding.ItemViewTabSelectionLayoutBinding
import com.didaagency.adopteunelivraison.interfaces.RvItemClickListner
import com.didaagency.adopteunelivraison.utils.AppUtils
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.view.baseViews.BaseRecyclerViewHolder


class OrderItemAdapter(val arrListData: List<OrderItems>?, private val mContext: Context) :
    RecyclerView.Adapter<OrderItemAdapter.MyViewHolder>() {

    var onItemClickListner: RvItemClickListner<OrderItems>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemViewOrderDetailLayoutBinding = ItemViewOrderDetailLayoutBinding.inflate(
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
        val binding: ItemViewOrderDetailLayoutBinding
        init {
            binding = itemView as ItemViewOrderDetailLayoutBinding
        }

        fun updateView(data: OrderItems) {
            binding.tvItemDescription.setText((data.description?.trim { it <= ' ' }))
            binding.tvItemPrice.setText((data.price?.trim { it <= ' ' }))
            binding.tvItemTitle.setText((data.title?.trim { it <= ' ' }))

            Glide
                .with(mContext)
                .load(data.image)
                .centerCrop()
                .placeholder(R.drawable.ic_home_new)
                .into(binding.ivOrder)
        }
    }

}


