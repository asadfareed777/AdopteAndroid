package com.didaagency.adopteunelivraison.view.dialogs

import android.app.Dialog
import android.content.ClipDescription
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.databinding.DialogAcceptOrderLayoutBinding

class AcceptOrderDialog (context: Context,val description: String) : Dialog(context,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen) {

    private var onClickListner : OnDialogItemClickListner? = null
    lateinit var binding:DialogAcceptOrderLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        window?.setLayout(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.MATCH_PARENT
//        )
        binding = DialogAcceptOrderLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()
       setClickListners()
    }

    private fun setData() {
        binding.tvDetail.setText(description)
    }

    private fun setClickListners() {
        binding.tvCancel.setOnClickListener {
            onClickListner?.onClick(false)
            dismiss()
        }
        binding.tvLessMyAccount.setOnClickListener {
            onClickListner?.onClick(true)
            dismiss()
        }
    }
    fun setOnClickListner(onItemClickListner: OnDialogItemClickListner){
        onClickListner = onItemClickListner
    }
    interface OnDialogItemClickListner {
        fun onClick(isYesNo: Boolean)
    }
}