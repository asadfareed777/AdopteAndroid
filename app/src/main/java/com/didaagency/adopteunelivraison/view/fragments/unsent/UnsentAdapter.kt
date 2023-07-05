package com.didaagency.adopteunelivraison.view.fragments.unsent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.data.database.entities.UnsentData
import com.didaagency.adopteunelivraison.data.network.request.SaveContainerRequest
import com.didaagency.adopteunelivraison.databinding.ItemUnsentBinding
import com.didaagency.adopteunelivraison.utils.API
import javax.inject.Inject

class UnsentAdapter @Inject constructor(): RecyclerView.Adapter<UnsentAdapter.UnsentViewHolder>(){

    inner class UnsentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val binding = ItemUnsentBinding.bind(itemView)
    }

    private val diffCallback = object : DiffUtil.ItemCallback<UnsentData>(){
        override fun areItemsTheSame(oldItem: UnsentData, newItem: UnsentData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UnsentData, newItem: UnsentData): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitList(list: List<UnsentData>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnsentViewHolder {
        return UnsentViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.item_unsent,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: UnsentViewHolder, position: Int) {

        val item = differ.currentList[position]
        with(holder){
            if (item.api == API.CONTAINER){
                val saveContainerRequest = Gson().fromJson(item.json, SaveContainerRequest::class.java)
                val dateTimeList: List<String> =
                    saveContainerRequest.datetime.split(" ")
                binding.tvDate.text = dateTimeList[0]
                binding.tvTime.text = dateTimeList[1]
            }
        }
       /* holder.itemView.apply {


        }*/
    }
}