package com.didaagency.adopteunelivraison.view.bottomSheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.didaagency.adopteunelivraison.databinding.BottomSheetConfirmationLayoutBinding
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.view.baseViews.BaseBottomSheetFragment

class ConfirmationBottomSheet(val title: String,val message: String) : BaseBottomSheetFragment() {

    private var onClickListner : OnItemClickListner? = null
    private lateinit var  binding : BottomSheetConfirmationLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetConfirmationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListners()
        setData()
    }

    private fun setData() {

        isCancelable = false
        binding.tvBsTitle.text = title
        binding.tvBsMessage.text = message
    }

    private fun setListners() {
        binding.tvBottomSheetNo.setOnClickListener {
            onClickListner?.onClick(false)
            dismiss()

        }
        binding.tvBottomSheetYes.setOnClickListener {
            onClickListner?.onClick(true)
            dismiss()
        }
    }

    fun setOnClickListner(onItemClickListner: OnItemClickListner){
        onClickListner = onItemClickListner
    }

    interface OnItemClickListner {
        fun onClick(isYesNo: Boolean)
    }
}