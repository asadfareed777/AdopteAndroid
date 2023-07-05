package com.didaagency.adopteunelivraison.utils

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.RequestBody
import java.io.File

object NetworkUtility {
    /*fun toRequestBody(value: String?): RequestBody {
        return RequestBody.create(parse.parse("text/plain"), value!!)
    }

    fun toRequestBodyFile(file: File?): RequestBody {
        return RequestBody.create(parse.parse("multipart/form-data"), file!!)
    }*/

    fun isNetworkAvailable(mContext: Context): Boolean {
        val connectivityManager =
            mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

   /* fun singleMultipartBodyJson(key: String?, file: File): Part {
        return createFormData.createFormData(
            key, file.name,
            toRequestBodyFile(file)
        )
    }*/
}
