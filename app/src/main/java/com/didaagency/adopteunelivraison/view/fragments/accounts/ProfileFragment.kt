package com.didaagency.adopteunelivraison.view.fragments.accounts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.databinding.ActivityForgotPasswordBinding
import com.didaagency.adopteunelivraison.databinding.ActivityProfileBinding
import com.didaagency.adopteunelivraison.databinding.FragmentDashboardBinding
import com.didaagency.adopteunelivraison.utils.*
import com.didaagency.adopteunelivraison.view.baseViews.BaseActivity
import com.didaagency.adopteunelivraison.view.baseViews.BaseFragment
import com.didaagency.adopteunelivraison.view.bottomSheets.ConfirmationBottomSheet
import com.didaagency.adopteunelivraison.view.forgotpassword.ForgotPasswordViewModel
import com.didaagency.adopteunelivraison.view.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    private lateinit var binding: ActivityProfileBinding
    private val viewModel: ProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setData()
    }

    private fun setData() {
        binding.tvUserName.text = "Hello, "+getLoggedInUser()?.contactName
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
                    ClickEvents.MANAGE_PROFILE_CLICK -> {
                        IntentUtils.callIntentWithFlags(requireContext(),EditProfileActivity::class.java,false)
                    }
                    ClickEvents.CHANGE_PASSWORD_CLICK -> {
                        IntentUtils.callIntentWithFlags(requireContext(),ChangePasswordActivity::class.java,false)
                    }
                    ClickEvents.PRINTER -> {

                    }
                    ClickEvents.LANGUAGE -> {

                    }
                    ClickEvents.DELETE -> {

                    }
                    ClickEvents.LEGAL -> {

                    }
                    ClickEvents.SIGN_OUT -> {
                        callLogoutBS()
                    }
                }
            }
        }


    }


    private fun callLogoutBS() {
        var bottomSheet = ConfirmationBottomSheet(resources.getString(R.string.text_logout),resources.getString(R.string.text_logout_description))
        bottomSheet.show(childFragmentManager, bottomSheet.tag)
        bottomSheet.setOnClickListner(object : ConfirmationBottomSheet.OnItemClickListner {
            override fun onClick(isYesNo: Boolean) {
                if (isYesNo) {
                    AppPreference.saveData(requireContext(),false, Constants.KEY_IS_LOGGED_IN)
                    AppPreference.DeleteSavedData(requireContext())
                    IntentUtils.callIntentWithFlags(requireContext(),
                        LoginActivity::class.java,true)
                }
            }
        })
    }

}