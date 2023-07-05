package com.didaagency.adopteunelivraison.view.fragments.unsent

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import com.didaagency.adopteunelivraison.data.database.entities.UnsentData
import com.didaagency.adopteunelivraison.data.network.request.SaveContainerRequest
import com.didaagency.adopteunelivraison.databinding.FragmentUnsentBinding
import com.didaagency.adopteunelivraison.interfaces.MainHandler
import com.didaagency.adopteunelivraison.utils.*
import com.didaagency.adopteunelivraison.view.baseViews.BaseFragment
import javax.inject.Inject

@AndroidEntryPoint
class UnsentFragment : BaseFragment() {

    private lateinit var binding: FragmentUnsentBinding
    private val unsentViewModel:UnsentViewModel by viewModels()
    private lateinit var mainHandler: MainHandler
    var adapterUnsent:UnsentAdapter? = null
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpObservers()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainHandler = context as MainHandler
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUnsentBinding.inflate(inflater,container,false)
        initData()
        initViews()
        setClickListeners()
        return binding.root
    }

    private fun initData() {
        unsentViewModel.getUnsentList()
    }

    private fun initViews() {
        binding.recyclerView.layoutManager =  LinearLayoutManager(requireActivity())
        binding.recyclerView.adapter = adapterUnsent
    }

    private fun setUpObservers() {
        lifecycleScope.launchWhenStarted {
            unsentViewModel.containerUnsentList.observe(viewLifecycleOwner) { unsentData ->
                try {
                    when (unsentData.status) {
                        Status.SUCCESS -> {
                            alertDialogUtils!!.hideAlertDialog()
                            if (unsentData.data != null && unsentData.data.isNotEmpty()){
                                adapterUnsent!!.submitList(unsentData.data)
                                binding.recyclerView.visibility = View.VISIBLE
                                binding.btnSubmit.visibility = View.VISIBLE
                                binding.tvNoRecordFound.visibility = View.INVISIBLE
                            }else{
                                binding.recyclerView.visibility = View.INVISIBLE
                                binding.btnSubmit.visibility = View.INVISIBLE
                                binding.tvNoRecordFound.visibility = View.VISIBLE
                                // TODO: Refresh data
                              //  mainHandler.refreshSyncData()
                            }
                        }
                        Status.ERROR -> {
                            hideLoadingDialog()
                            showToast(unsentData.message.toString())
                            binding.recyclerView.visibility = View.INVISIBLE
                            binding.btnSubmit.visibility = View.INVISIBLE
                            binding.tvNoRecordFound.visibility = View.VISIBLE
                        }
                        Status.LOADING -> {
                            showLoadingDialog()
                        }
                    }
                }catch (exception:Exception){
                    exception.printStackTrace()
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            unsentViewModel.updateContainerResponse.observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        try {
                            val containerData: String = Gson().toJson(it.data)
                            unsentViewModel.clearSentData(containerData)
                            /*val unsentData = UnsentData(
                                api = API.ATTENDANCE,
                                json = containerData
                            )
                            updateList(unsentData)*/
                        }catch (exception:Exception){
                            exception.printStackTrace()
                        }
                        hideLoadingDialog()
                    }
                    Status.ERROR -> {
                        hideLoadingDialog()
                        showToast(it.message.toString())
                    }
                    Status.LOADING -> {
                        showLoadingDialog()
                    }
                }
            }
        }
    }

    private fun updateList(unsentData: UnsentData) {
        if (unsentViewModel.containerUnsentList.value != null &&
            unsentViewModel.containerUnsentList.value!!.data != null) {
            val unsentList: ArrayList<UnsentData> =
                unsentViewModel.containerUnsentList.value!!.data as ArrayList
            if (unsentList.isNotEmpty()) {
                unsentList.remove(unsentData)
                adapterUnsent!!.submitList(unsentList)
            }
        }
    }

    private fun setClickListeners() {
        binding.btnSubmit.setOnClickListener {
            if (NetworkUtility.isNetworkAvailable(requireActivity())) {
                if (unsentViewModel.containerUnsentList.value != null &&
                    unsentViewModel.containerUnsentList.value!!.data != null){
                    val unsentList:ArrayList<UnsentData> = unsentViewModel.containerUnsentList.value!!.data as ArrayList
                    if (unsentList.isNotEmpty()){
                        CoroutineScope(Dispatchers.Main).launch {
                            unsentList.forEachIndexed { index, unsentData ->
                                showLoadingDialog()
                                delay(1000)
                                hideLoadingDialog()
                                handleApiCalls(unsentData)
                                if (index == unsentList.size-1){
                                    unsentViewModel.getUnsentList()
                                }
                            }
                        }
                      //  unsentViewModel.getUnsentList()
                    }
                }
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Internet not available!!!. Try again later",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

        }
    }

    private suspend fun handleApiCalls(unsentData: UnsentData) {
        coroutineScope {
            when (unsentData.api) {
                API.CONTAINER -> {
                    val saveContainerRequest =
                        Gson().fromJson(unsentData.json, SaveContainerRequest::class.java)
//                    unsentViewModel.updateContainer(saveContainerRequest)
                }
                else -> {}
            }
        }
    }

}