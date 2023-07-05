package com.didaagency.adopteunelivraison.view.adapters.merchant

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.didaagency.adopteunelivraison.data.network.request.merchant.MerchantMenu
import com.didaagency.adopteunelivraison.data.network.response.merchant.PayoutHistory
import com.didaagency.adopteunelivraison.data.network.response.merchant.TransactionHistory
import com.didaagency.adopteunelivraison.databinding.ItemViewMerchantMenuGridLayoutBinding
import com.didaagency.adopteunelivraison.databinding.ItemViewPayoutHistoryLayoutBinding
import com.didaagency.adopteunelivraison.databinding.ItemViewTransactionHistoryLayoutBinding
import com.didaagency.adopteunelivraison.interfaces.RvItemClickListner
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.view.baseViews.BaseRecyclerViewHolder


class PayoutHistoryAdapter(val arrListData: List<PayoutHistory>?, private val mContext: Context) :
    RecyclerView.Adapter<PayoutHistoryAdapter.MyViewHolder>() {

     var onTabItemClickListner: RvItemClickListner<PayoutHistory>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemViewPayoutHistoryLayoutBinding = ItemViewPayoutHistoryLayoutBinding.inflate(
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
        holder.binding.llParent.setOnClickListener { v ->
            onTabItemClickListner!!.onItemCLick(ClickEvents.RV_PARENT_CLICK,arrListData!![position], position)
        }
    }

    override fun getItemCount(): Int {
        return if (arrListData == null) 0 else arrListData!!.size
    }


    inner class MyViewHolder(itemView: ViewBinding) :
        BaseRecyclerViewHolder(itemView.root) {
        val binding: ItemViewPayoutHistoryLayoutBinding
        init {
            binding = itemView as ItemViewPayoutHistoryLayoutBinding
        }

        fun updateView(data: PayoutHistory) {
            binding.tvTransactionDescription.setText((data.transactionDetails?.trim { it <= ' ' }))
            binding.tvTransactionDate.setText((data.transactionDate?.trim { it <= ' ' }))
            binding.tvTransactionPrice.setText((data.transactionPrice?.trim { it <= ' ' }))
            binding.tvTransactionStatus.setText((data.transactionStatus?.trim { it <= ' ' }))
        }
    }

}


