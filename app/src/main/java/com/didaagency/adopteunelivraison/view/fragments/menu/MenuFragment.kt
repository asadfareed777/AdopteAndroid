package com.didaagency.adopteunelivraison.view.fragments.menu

import android.os.Bundle
import android.util.LayoutDirection
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.databinding.FragmentMenuBinding
import com.didaagency.adopteunelivraison.interfaces.RvItemClickListner
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.view.adapters.TabSelectItemAdapter
import com.didaagency.adopteunelivraison.view.baseViews.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MenuFragment : BaseFragment() {

    private lateinit var binding: FragmentMenuBinding
    private val viewModel: MenuViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setData()
    }

    private fun setData() {
        var tabArrays = arrayOf<String>("Menu Items", "Addons", "Size", "Ingredients")
        binding.rvTabs.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        var tabSelectItemAdapter = TabSelectItemAdapter(tabArrays.toList(),requireContext())
        binding.rvTabs.adapter = tabSelectItemAdapter
        tabSelectItemAdapter.onTabItemClickListner = object : RvItemClickListner<String> {
            override fun onItemCLick(clickEvent: ClickEvents?, item: String?, position: Int) {
                when(clickEvent){
                    ClickEvents.RV_PARENT_CLICK -> {
                        showToast(item.toString())
                    }
                }
            }
        }
    }
    private fun initViews() {
        binding.model = viewModel
        setUpObservers()
    }
    private fun setUpObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.clickEvent.observe(requireActivity()) {
                when (it) {
                    ClickEvents.BACK -> {
                        activity?.finish()
                    }
                    ClickEvents.INTERNET_CONNECTION -> {
                        showToast(resources.getString(R.string.internet_connection_message))
                    }

                }
            }
        }
    }

}