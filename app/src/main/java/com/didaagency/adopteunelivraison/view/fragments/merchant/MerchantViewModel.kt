package com.didaagency.adopteunelivraison.view.fragments.merchant

import com.didaagency.adopteunelivraison.data.repositry.AuthRepository
import com.didaagency.adopteunelivraison.data.repositry.MerchantRepository
import com.didaagency.adopteunelivraison.view.baseViews.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MerchantViewModel @Inject constructor(
    private val repository: MerchantRepository
) : BaseViewModel() {
}