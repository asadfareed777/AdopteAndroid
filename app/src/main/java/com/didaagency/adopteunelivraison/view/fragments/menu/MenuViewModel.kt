package com.didaagency.adopteunelivraison.view.fragments.menu

import com.didaagency.adopteunelivraison.data.repositry.AuthRepository
import com.didaagency.adopteunelivraison.view.baseViews.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

}