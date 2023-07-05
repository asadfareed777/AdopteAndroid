package com.didaagency.adopteunelivraison.utils

import android.content.Intent
import android.location.Location

class Constants {

    companion object{
        var BASE_URL:String = ""
        var BASE_URL_API:String = ""
        var CURRENT_LOCATION: Location? = null
        const val DATA_TIME_FORMAT_24_HOUR = "yyyy-MM-dd HH:mm:ss"
        const val CNIC_LENGTH = 15
        const val SUCCESS_CODE = 200
        const val DATABASE_NAME = "Adopt_Db"
        const val FIRST_TIME_REG = "FIRST_TIME_REG"

        const val KEY_IS_LOGGED_IN = "LOGGED_IN"
        const val KEY_USER_ID = "User_id"
        const val KEY_USER_PASSOWRD = "User_password"
        const val KEY_USER_PERMISSION = "User_permissions"
        const val KEY_ACCESS_TOKEN = "token"
        const val KEY_USER = "user_data"
        const val KEY_ONLINE_STATUS = "user_online_status"



        //  Apis End Points
        const val API_LOGIN = "api/login"
        const val API_REGISTER = "api/merchants/profiles"
        const val API_MERCHANT_PROFILE = "api/merchants/profiles"
        const val API_MERCHANT_ORDER_STATS = "api/merchants/order_stats"
        const val API_MERCHANT_ORDERS = "api/merchants/orders"

    }
}