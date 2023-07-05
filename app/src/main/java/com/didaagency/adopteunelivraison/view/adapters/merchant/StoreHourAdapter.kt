package com.didaagency.adopteunelivraison.view.adapters.merchant

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.data.network.request.merchant.MerchantMenu
import com.didaagency.adopteunelivraison.data.network.response.merchant.StoreHour
import com.didaagency.adopteunelivraison.databinding.ItemViewMerchantMenuGridLayoutBinding
import com.didaagency.adopteunelivraison.databinding.ItemViewStoreHourLayoutBinding
import com.didaagency.adopteunelivraison.interfaces.RvItemClickListner
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.view.baseViews.BaseRecyclerViewHolder


class StoreHourAdapter(val arrListData: List<StoreHour>?, private val mContext: Context) :
    RecyclerView.Adapter<StoreHourAdapter.MyViewHolder>() {

     var onTabItemClickListner: RvItemClickListner<StoreHour>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemViewStoreHourLayoutBinding = ItemViewStoreHourLayoutBinding.inflate(
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
        val binding: ItemViewStoreHourLayoutBinding
        init {
            binding = itemView as ItemViewStoreHourLayoutBinding
        }

        fun updateView(data: StoreHour) {
            binding.tvDay.setText((data.day?.trim { it <= ' ' }))
            binding.tvTimings.setText(((data.from +" - "+data.to)?.trim { it <= ' ' }))
            if (data.isOpenColse){
                binding.tvStatus.setText(mContext.resources.getString(R.string.text_open))
            }else{
                binding.tvStatus.setText(mContext.resources.getString(R.string.text_close_store))
            }
        }
    }

}


