package com.didaagency.adopteunelivraison.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.databinding.ItemViewReasonLayoutBinding
import com.didaagency.adopteunelivraison.databinding.ItemViewTabSelectionLayoutBinding
import com.didaagency.adopteunelivraison.interfaces.RvItemClickListner
import com.didaagency.adopteunelivraison.utils.AppUtils
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.view.baseViews.BaseRecyclerViewHolder


class RejectReasonAdapter(val arrListData: List<String>?, private val mContext: Context) :
    RecyclerView.Adapter<RejectReasonAdapter.MyViewHolder>() {

    var selected_position = 0 // You have to set this globally in the Adapter class
    var onTabItemClickListner: RvItemClickListner<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemViewReasonLayoutBinding = ItemViewReasonLayoutBinding.inflate(
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
        if (selected_position == position) {
            onTabItemClickListner!!.onItemCLick(ClickEvents.RV_PARENT_CLICK,arrListData!![position], position)
            holder.binding.tvTitle.setBackground(mContext.resources.getDrawable(R.drawable.bg_solid))
            holder.binding.tvTitle.setTextColor(mContext.resources.getColor(R.color.white))
        } else {
            holder.binding.tvTitle.setBackgroundColor(mContext.resources.getColor(R.color.white))
            holder.binding.tvTitle.setTextColor(mContext.resources.getColor(R.color.black))
        }
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
        val binding: ItemViewReasonLayoutBinding
        init {
            binding = itemView as ItemViewReasonLayoutBinding
        }

        fun updateView(data: String) {
            binding.tvTitle.setText((data.trim { it <= ' ' }))
        }
    }

}


