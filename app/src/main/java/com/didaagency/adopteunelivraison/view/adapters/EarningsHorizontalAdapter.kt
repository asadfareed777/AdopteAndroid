package com.didaagency.adopteunelivraison.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.data.network.request.orders.Earnings
import com.didaagency.adopteunelivraison.databinding.ItemViewEarningsLayoutBinding
import com.didaagency.adopteunelivraison.databinding.ItemViewTabSelectionLayoutBinding
import com.didaagency.adopteunelivraison.interfaces.RvItemClickListner
import com.didaagency.adopteunelivraison.utils.AppUtils
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.view.baseViews.BaseRecyclerViewHolder


class EarningsHorizontalAdapter(val arrListData: List<Earnings>?, private val mContext: Context) :
    RecyclerView.Adapter<EarningsHorizontalAdapter.MyViewHolder>() {

    var selected_position = 0 // You have to set this globally in the Adapter class
    var onTabItemClickListner: RvItemClickListner<Earnings>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemViewEarningsLayoutBinding = ItemViewEarningsLayoutBinding.inflate(
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
        holder.binding.tvTitle.setOnClickListener { v ->
            notifyItemChanged(selected_position)
            selected_position = position
            notifyItemChanged(selected_position)
            onTabItemClickListner!!.onItemCLick(ClickEvents.RV_PARENT_CLICK,arrListData!![position], position)
        }
    }

    override fun getItemCount(): Int {
        return if (arrListData == null) 0 else arrListData!!.size
    }


    inner class MyViewHolder(itemView: ViewBinding) :
        BaseRecyclerViewHolder(itemView.root) {
        val binding: ItemViewEarningsLayoutBinding
        init {
            binding = itemView as ItemViewEarningsLayoutBinding
        }

        fun updateView(data: Earnings) {
            binding.tvEarning.setText((data.earnings.trim { it <= ' ' }))
            binding.tvTitle.setText((data.title.trim { it <= ' ' }))
//            binding.llParent.setBackgroundColor(Color.parseColor(data.bgColor));
            binding.llParent.backgroundTintList = ColorStateList.valueOf(Color.parseColor(data.bgColor))
        }
    }

}


