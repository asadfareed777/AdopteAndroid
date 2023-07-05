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
import com.didaagency.adopteunelivraison.databinding.BottomSheetRequestPayoutBinding
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.view.baseViews.BaseBottomSheetFragment

class RequestPayoutBottomSheet() : BaseBottomSheetFragment() {

    private var onClickListner : OnItemClickListner? = null
    private lateinit var  binding : BottomSheetRequestPayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetRequestPayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListners()
        setData()
    }

    private fun setData() {
        isCancelable = false
        binding.tvAccountType.setText("Paypal")

    }

    private fun setListners() {
        binding.tvCancel.setOnClickListener {
            onClickListner?.onClick(ClickEvents.CANCEL)
            dismiss()

        }
        binding.tvSubmit.setOnClickListener {
            if (isValidate()) {
                onClickListner?.onClick(ClickEvents.SUBMIT)
                dismiss()
            }
        }

        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }

    private fun isValidate(): Boolean {
        if (binding.etAmount.text.toString().trim().isEmpty()){
            binding.etAmount.error = "Please enter valid amount"
            return false
        }
        return true
    }

    fun setOnClickListner(onItemClickListner: OnItemClickListner){
        onClickListner = onItemClickListner
    }

    interface OnItemClickListner {
        fun onClick(event: ClickEvents)
    }
}