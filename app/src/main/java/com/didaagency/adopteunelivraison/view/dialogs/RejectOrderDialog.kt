package com.didaagency.adopteunelivraison.view.dialogs

import android.app.Dialog
import android.content.ClipDescription
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.databinding.DialogRejectOrderLayoutBinding
import com.didaagency.adopteunelivraison.interfaces.RvItemClickListner
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.view.adapters.RejectReasonAdapter
import com.didaagency.adopteunelivraison.view.adapters.TabSelectItemAdapter

class RejectOrderDialog (context: Context, val description: String) : Dialog(context,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen) {

    private var onClickListner : OnDialogItemClickListner? = null
    lateinit var binding:DialogRejectOrderLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogRejectOrderLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()
       setClickListners()
    }

    private fun setData() {
        setResonData()
    }

    private fun setResonData() {
        var tabArrays = arrayOf<String>("Out of Items", "There is no posibility of ful fil the order"
            ,"Today we are no longer delivering", "Too long wait time", "No ingredients", "Customer called to cancel",
            "Resturant too busy","Other"
        )
        binding.rvReasons.layoutManager = LinearLayoutManager(context,
            RecyclerView.VERTICAL,false)
        var adapter = RejectReasonAdapter(tabArrays.toList(),context)
        binding.rvReasons.adapter = adapter
        adapter.onTabItemClickListner = object :RvItemClickListner<String>{
            override fun onItemCLick(clickEvent: ClickEvents?, item: String?, position: Int) {
                binding.etReason.setText(item)
            }
        }

    }

    private fun setClickListners() {
        binding.tvCancel.setOnClickListener {
            onClickListner?.onClick(false)
            dismiss()
        }
        binding.ivClose.setOnClickListener {
            onClickListner?.onClick(false)
            dismiss()
        }
        binding.tvReject.setOnClickListener {
            if (binding.etReason.text.toString().trim().isEmpty()){
                Toast.makeText(context,"Please Select Reason",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
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