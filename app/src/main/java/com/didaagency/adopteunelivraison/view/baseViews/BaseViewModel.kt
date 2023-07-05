package com.didaagency.adopteunelivraison.view.baseViews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.didaagency.adopteunelivraison.data.network.response.User
import com.didaagency.adopteunelivraison.utils.ClickEvents
import com.didaagency.adopteunelivraison.utils.Resource

open class BaseViewModel : ViewModel() {

    var clickEvent = MutableLiveData<ClickEvents>()


    fun onBackClick() {
        clickEvent.value = ClickEvents.BACK
    }
    fun onSideDrawerClick() {
        clickEvent.value = ClickEvents.OPEN_CLOSE_DRAWE
    }
    fun onNotificationClick() {
        clickEvent.value = ClickEvents.NOTIFICATION
    }
    fun onHomeClick() {
        clickEvent.value = ClickEvents.HOME
    }
    fun onSyncDataClick() {
        clickEvent.value = ClickEvents.SYNC_DATA
    }
    fun onLogoutClick() {
        clickEvent.value = ClickEvents.LOGOUT
    }
    fun internetConnectionError() {
        clickEvent.value = ClickEvents.INTERNET_CONNECTION
    }

    fun onLoginClick() {
        clickEvent.value = ClickEvents.LOGIN
    }
    fun onForgetPasswordClick() {
        clickEvent.value = ClickEvents.FORGET_PASSWORD_CLICK
    }
    fun onEyePasswordClick() {
        clickEvent.value = ClickEvents.EYE_PASSWORD_CLICK
    }
    fun onNewEyePasswordClick() {
        clickEvent.value = ClickEvents.NEW_EYE_PASSWORD_CLICK
    }
    fun onConfirmEyePasswordClick() {
        clickEvent.value = ClickEvents.CONFIRM_EYE_PASSWORD_CLICK
    }
    fun onSignUpClick() {
        clickEvent.value = ClickEvents.SIGNUP
    }

    fun onResetPasswordClick() {
        clickEvent.value = ClickEvents.RESET_PASSWORD
    }
    fun onSearchClick() {
        clickEvent.value = ClickEvents.SEARCH_ICON_CLICK
    }
    fun onManageProfileClick() {
        clickEvent.value = ClickEvents.MANAGE_PROFILE_CLICK
    }
    fun onChangePasswordClick() {
        clickEvent.value = ClickEvents.CHANGE_PASSWORD_CLICK
    }
    fun onPushNotificationClick() {
        clickEvent.value = ClickEvents.NOTIFICATION
    }
    fun onLocalNotificationClick() {
        clickEvent.value = ClickEvents.LOCAL_NOTIFICATION
    }
    fun onPrinterClick() {
        clickEvent.value = ClickEvents.PRINTER
    }
    fun onLanguageClick() {
        clickEvent.value = ClickEvents.LANGUAGE
    }
    fun onDeleteClick() {
        clickEvent.value = ClickEvents.DELETE
    }
    fun onLegalClick() {
        clickEvent.value = ClickEvents.LEGAL
    }
    fun onSignOutClick() {
        clickEvent.value = ClickEvents.SIGN_OUT
    }
    fun onCameraClick() {
        clickEvent.value = ClickEvents.CAMERA_REQUEST
    }
    fun onUpdateClick() {
        clickEvent.value = ClickEvents.UPDATE
    }
    fun onOrderTimeLineClick() {
        clickEvent.value = ClickEvents.ORDER_TIMELINE
    }
    fun onDeliveryInfoClick() {
        clickEvent.value = ClickEvents.DELIVERY_INFO
    }

    fun onAcceptedClick() {
        clickEvent.value = ClickEvents.ACCEPTED
    }
    fun onRejectedClick() {
        clickEvent.value = ClickEvents.REJECTED
    }

    fun onRequestPayoutClick() {
        clickEvent.value = ClickEvents.REQUEST_PAYOUT
    }

    fun onChangeLogoClick() {
        clickEvent.value = ClickEvents.CHANGE_LOGO
    }

    fun onSubmitClick() {
        clickEvent.value = ClickEvents.SUBMIT
    }

    fun onAddClick() {
        clickEvent.value = ClickEvents.ADD
    }

    fun onMapLocationClick() {
        clickEvent.value = ClickEvents.MAP_LOCATION
    }


    fun onEditClick() {
        clickEvent.value = ClickEvents.EDIT
    }

    fun onFromClick() {
        clickEvent.value = ClickEvents.From
    }

    fun onToClick() {
        clickEvent.value = ClickEvents.TO
    }

}