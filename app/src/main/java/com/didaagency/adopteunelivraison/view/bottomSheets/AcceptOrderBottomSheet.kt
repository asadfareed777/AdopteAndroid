package com.didaagency.adopteunelivraison.view.bottomSheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.data.network.request.orders.Orders
import com.didaagency.adopteunelivraison.databinding.BottomSheetAcceptOrderBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.didaagency.adopteunelivraison.databinding.BottomSheetConfirmationLayoutBinding
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.view.baseViews.BaseBottomSheetFragment

class AcceptOrderBottomSheet(val data: Orders) : BaseBottomSheetFragment() {

    private var onClickListner : OnItemClickListner? = null
    private lateinit var  binding : BottomSheetAcceptOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetAcceptOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListners()
        setData()
    }

    private fun setData() {
//        binding.tvBsTitle.text = title
//        binding.tvBsMessage.text = message

        isCancelable = false
        binding.tvOrderId.setText(("Order # "+data.orderId?.toString()?.trim { it <= ' ' }))
        binding.incOrderDetail.tvItemDescription.setText((data.description?.trim { it <= ' ' }))
        binding.incOrderDetail.tvItemPrice.setText((data.price?.trim { it <= ' ' }))
        binding.incOrderDetail.tvItemTitle.setText((data.title?.trim { it <= ' ' }))

        Glide
            .with(requireActivity())
            .load(data.image)
            .centerCrop()
            .placeholder(R.drawable.ic_home_new)
            .into(binding.incOrderDetail.ivOrder);
    }

    private fun setListners() {
        binding.tvAccept.setOnClickListener {
            onClickListner?.onClick(ClickEvents.ACCEPTED)
            dismiss()

        }
        binding.tvReject.setOnClickListener {
            onClickListner?.onClick(ClickEvents.REJECTED)
            dismiss()
        }
        binding.tvFullOrderDetail.setOnClickListener {
            onClickListner?.onClick(ClickEvents.ORDER_DETAIL)
            dismiss()
        }
        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }

    fun setOnClickListner(onItemClickListner: OnItemClickListner){
        onClickListner = onItemClickListner
    }

    interface OnItemClickListner {
        fun onClick(event: ClickEvents)
    }
}