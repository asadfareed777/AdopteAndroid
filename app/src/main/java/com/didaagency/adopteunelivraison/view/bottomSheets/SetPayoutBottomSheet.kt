package com.didaagency.adopteunelivraison.view.bottomSheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.data.network.request.orders.Orders
import com.didaagency.adopteunelivraison.databinding.BottomSheetAcceptOrderBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.didaagency.adopteunelivraison.databinding.BottomSheetConfirmationLayoutBinding
import com.didaagency.adopteunelivraison.databinding.BottomSheetRequestPayoutBinding
import com.didaagency.adopteunelivraison.databinding.BottomSheetSetPayoutBinding
import com.didaagency.adopteunelivraison.interfaces.RvItemClickListner
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.view.adapters.TabSelectItemAdapter
import com.didaagency.adopteunelivraison.view.baseViews.BaseBottomSheetFragment

class SetPayoutBottomSheet() : BaseBottomSheetFragment() {

    private var onClickListner : OnItemClickListner? = null
    private lateinit var  binding : BottomSheetSetPayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetSetPayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListners()
        setData()
    }

    private fun setData() {
        isCancelable = false
        setTabData()

    }

    private fun setTabData() {
        var tabArrays = arrayOf<String>("Paypal", "Stripe", "Bank Transfer")
        binding.rvPayoutAccount.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        var tabSelectItemAdapter = TabSelectItemAdapter(tabArrays.toList(), requireContext())
        binding.rvPayoutAccount.adapter = tabSelectItemAdapter
        tabSelectItemAdapter.onTabItemClickListner = object : RvItemClickListner<String> {
            override fun onItemCLick(clickEvent: ClickEvents?, item: String?, position: Int) {
                when (clickEvent) {
                    ClickEvents.RV_PARENT_CLICK -> {
                        if (item.toString().equals("Paypal", true)) {
                            binding.llPaypal.visibility = VISIBLE
                            binding.llBankTransfer.visibility = GONE
                            binding.llStripe.visibility = GONE
                        } else if (item.toString().equals("Stripe", true)) {
                            binding.llPaypal.visibility = GONE
                            binding.llBankTransfer.visibility = GONE
                            binding.llStripe.visibility = VISIBLE
                        } else if (item.toString().equals("Bank Transfer", true)) {
                            binding.llPaypal.visibility = GONE
                            binding.llBankTransfer.visibility = VISIBLE
                            binding.llStripe.visibility = GONE
                        }
                    }
                }
            }
        }
        tabSelectItemAdapter.setDefaultClick()

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
//        if (binding.etAmount.text.toString().trim().isEmpty()){
//            binding.etAmount.error = "Please enter valid amount"
//            return false
//        }
        return true
    }

    fun setOnClickListner(onItemClickListner: OnItemClickListner){
        onClickListner = onItemClickListner
    }



    interface OnItemClickListner {
        fun onClick(event: ClickEvents)
    }
}