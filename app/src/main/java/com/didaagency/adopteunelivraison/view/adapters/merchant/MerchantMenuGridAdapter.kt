package com.didaagency.adopteunelivraison.view.adapters.merchant

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.didaagency.adopteunelivraison.data.network.request.merchant.MerchantMenu
import com.didaagency.adopteunelivraison.databinding.ItemViewMerchantMenuGridLayoutBinding
import com.didaagency.adopteunelivraison.interfaces.RvItemClickListner
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.view.baseViews.BaseRecyclerViewHolder


class MerchantMenuGridAdapter(val arrListData: List<MerchantMenu>?, private val mContext: Context) :
    RecyclerView.Adapter<MerchantMenuGridAdapter.MyViewHolder>() {

     var onTabItemClickListner: RvItemClickListner<MerchantMenu>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemViewMerchantMenuGridLayoutBinding = ItemViewMerchantMenuGridLayoutBinding.inflate(
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
        val binding: ItemViewMerchantMenuGridLayoutBinding
        init {
            binding = itemView as ItemViewMerchantMenuGridLayoutBinding
        }

        fun updateView(data: MerchantMenu) {
            binding.ivTitle.setImageResource(data.image!!)
            binding.tvTitle.setText((data.title?.trim { it <= ' ' }))
        }
    }

}


