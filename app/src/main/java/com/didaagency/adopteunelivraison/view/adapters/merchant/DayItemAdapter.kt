package com.didaagency.adopteunelivraison.view.adapters.merchant

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.databinding.ItemViewDayLayoutBinding
import com.didaagency.adopteunelivraison.databinding.ItemViewTabSelectionLayoutBinding
import com.didaagency.adopteunelivraison.interfaces.RvItemClickListner
import com.didaagency.adopteunelivraison.utils.AppUtils
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.view.baseViews.BaseRecyclerViewHolder


class DayItemAdapter(val arrListData: List<String>?, private val mContext: Context) :
    RecyclerView.Adapter<DayItemAdapter.ViewHolderTabSelection>() {

    var selected_position = 0 // You have to set this globally in the Adapter class
    var onTabItemClickListner: RvItemClickListner<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTabSelection {
        val binding: ItemViewDayLayoutBinding = ItemViewDayLayoutBinding.inflate(
            LayoutInflater.from(
                mContext
            ), parent, false
        )
        return ViewHolderTabSelection(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolderTabSelection,
        @SuppressLint("RecyclerView") position: Int
    ) {
        if (selected_position == position) {
//            onTabItemClickListner!!.onItemCLick(ClickEvents.RV_PARENT_CLICK,arrListData!![position], position)
            holder.binding.tvTabTitle.setBackground(mContext.resources.getDrawable(R.drawable.bg_solid_rounded))
            holder.binding.tvTabTitle.setTextColor(mContext.resources.getColor(R.color.white))
        } else {
            holder.binding.tvTabTitle.setBackground(mContext.resources.getDrawable(R.drawable.bg_outline_rounded))
            holder.binding.tvTabTitle.setTextColor(mContext.resources.getColor(R.color.appColor))
        }
        holder.updateView(arrListData!![position])
        holder.binding.tvTabTitle.setOnClickListener { v ->
            notifyItemChanged(selected_position)
            selected_position = position
            notifyItemChanged(selected_position)
            onTabItemClickListner!!.onItemCLick(ClickEvents.RV_PARENT_CLICK,arrListData!![position], position)
        }
    }

    override fun getItemCount(): Int {
        return if (arrListData == null) 0 else arrListData!!.size
    }


    inner class ViewHolderTabSelection(itemView: ViewBinding) :
        BaseRecyclerViewHolder(itemView.root) {
        val binding: ItemViewDayLayoutBinding
        init {
            binding = itemView as ItemViewDayLayoutBinding
        }

        fun updateView(data: String) {
            binding.tvTabTitle.setText((data.trim { it <= ' ' }))
        }
    }

}


