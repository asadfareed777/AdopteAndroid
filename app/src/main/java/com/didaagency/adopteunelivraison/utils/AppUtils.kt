package com.didaagency.adopteunelivraison.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import android.text.method.PasswordTransformationMethod
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.didaagency.adopteunelivraison.BuildConfig
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.base.BaseApp
import com.didaagency.adopteunelivraison.databinding.BottomSheetConfirmationLayoutBinding
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.List
import java.util.regex.Pattern
import javax.inject.Inject


class AppUtils @Inject constructor(){
    fun OnGPS(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton(
            "Yes"
        ) { dialog, which -> context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
            .setNegativeButton(
                "No"
            ) { dialog, which -> dialog.cancel() }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun getDefaultHeaders(accessToken: Boolean): Map<String, String>? {
        val header: MutableMap<String, String> = HashMap()
        header["DEVICE_TYPE"] = "android"
        header["OS_VERSION"] = Build.VERSION.RELEASE + ""
        header["APP_VERSION"] = BuildConfig.VERSION_NAME + ""
        header["Accept"] = "application/json"
        if (accessToken) {
            header["user_id"] = AppPreference.getValue(BaseApp.instance,Constants.KEY_USER_ID).toString()
//            header["access_token"] = AppPreference.getValue(BaseApp.instance,Constants.KEY_ACCESS_TOKEN).toString()
            header["Authorization"] = "Bearer "+AppPreference.getValue(BaseApp.instance,Constants.KEY_ACCESS_TOKEN).toString()
        }
        return header
    }
    companion object {
        private var progressDialog: Dialog? = null
        private var placeFirstDash = false
        private var placeSecondDash = false

        fun hideSoftKeyboard(callingActivity: Activity) {
            if (callingActivity.currentFocus != null) {
                val inputManager = callingActivity
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(
                    callingActivity
                        .currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }

        fun showCustomProgress(context: Context?, message: String): Dialog? {
            val factory = LayoutInflater.from(context)
            val view: View = factory.inflate(R.layout.progress_bar, null)
            progressDialog = Dialog(context!!)
            progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progressDialog!!.setContentView(view)
            progressDialog!!.setCancelable(false)
            if (message != "") {
                val waitingMessage = progressDialog!!.findViewById<TextView>(R.id.tv_message)
                waitingMessage.text = message
            }
            return progressDialog
        }

        fun hideCustomProgress(){
            if (progressDialog != null){
                progressDialog!!.hide()
            }
        }

        fun showConfirmationDialog(
            context: Context,
            msg: String,
            title: String,
            okText: String,
            oKClickListener: View.OnClickListener?,
            cancelClickListener: View.OnClickListener?
        ) {
            val activity = context as Activity
            val viewGroup = activity.findViewById<ViewGroup>(android.R.id.content)

            lateinit var  binding : BottomSheetConfirmationLayoutBinding
            binding = BottomSheetConfirmationLayoutBinding.inflate(activity.layoutInflater, viewGroup, false)

            val builder = AlertDialog.Builder(context)
            builder.setView(binding.root)
            val alertDialog = builder.create()
            alertDialog.setCancelable(true)
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.show()

            if (!msg.isEmpty()) {
                binding.tvBsMessage.text = msg
            }
            if (!title.isEmpty()) {
                binding.tvBsTitle.text = title
            }
            if (!okText.isEmpty()) {
                binding.tvBottomSheetYes.text = okText
                binding.tvBottomSheetNo.text = activity.resources.getString(R.string.text_cancel)
            }
            binding.tvBottomSheetYes.tag = alertDialog
            binding.tvBottomSheetYes.setOnClickListener(oKClickListener)
            binding.tvBottomSheetNo.tag = alertDialog
            binding.tvBottomSheetNo.setOnClickListener(cancelClickListener)
        }




        fun isValidPhoneNumber(number: String): Boolean {
            if (number.length == 12) {
                if (number.startsWith("03")) {
                    return true
                }
            }
            if (number.length == 11 && !number.contains("-")) {
                if (number.startsWith("03")) {
                    return true
                }
            }
            return false
        }

        fun isValidCNIC(cnicNumber: String): Boolean {
            if (cnicNumber.trim { it <= ' ' }.replace("-".toRegex(), "").length == 13) {
                if (cnicNumber.replace("0".toRegex(), "").length > 0) {
                    return true
                }
            }
            return false
        }


        fun hideShowPassword(editText: EditText, imageView: ImageView) {
            if (editText.transformationMethod == null) {
                editText.transformationMethod = PasswordTransformationMethod()
                imageView.setImageResource(R.drawable.ic_eye_hide)
            } else {
                editText.transformationMethod = null
                imageView.setImageResource(R.drawable.ic_eye)
            }
            editText.setSelection(editText.text.length)
        }
        fun getBytes(bitmap: Bitmap): ByteArray {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            return stream.toByteArray()
        }

        fun getImage(image: ByteArray): Bitmap {
            return BitmapFactory.decodeByteArray(image, 0, image.size)
        }

        fun getBase64(bitmap: Bitmap): String {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
        }

        fun base64ToByte(base64: String?): ByteArray {
            return Base64.decode(base64, Base64.DEFAULT)
        }

        fun base64ToBitmap(encodedImage: String?): Bitmap {
            return getImage(Base64.decode(encodedImage, Base64.DEFAULT))
        }

        fun getJsonObject(tag: String?, value: String?): JSONObject? {
            val jsonObject = JSONObject()
            try {
                jsonObject.put("tag", tag)
                jsonObject.put("data", value)
                return jsonObject
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return null
        }

        fun getKey(str: String): String {
            val keyWords = List.of("s/o", "W/O", "S/o", "S/O")
            var i = 0
            while (getTrimmedString(str, keyWords[i])[0] === str) {
                i += 1
            }
            return keyWords[i]
        }

        fun getTrimmedString(str: String, key: String): Array<String> {
            return str.split(key.toRegex()).toTypedArray()
        }

        fun getLocation(mContext: Context): String? {
            val locationManager = mContext.getSystemService(LOCATION_SERVICE) as LocationManager
            var locationStr: String? = null
            if (ActivityCompat.checkSelfPermission(
                    mContext, Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    mContext, Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    (mContext as Activity),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
            } else {
                val locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (locationGPS != null) {
                    locationStr = locationGPS.latitude.toString() + "," + locationGPS.longitude
                }
            }
            return locationStr
        }

        fun formattedCnic(context: Context, editText: EditText, s: CharSequence) {
            var str = s.toString()
            if (!placeFirstDash && s.length == 5) {
                placeFirstDash = true
                str += "-"
                editText.setText(str)
                editText.setSelection(str.length)
            } else if (placeFirstDash && s.length < 5) {
                placeFirstDash = false
            }
            if (!placeSecondDash && s.length == 13) {
                placeSecondDash = true
                str += "-"
                editText.setText(str)
                editText.setSelection(str.length)
            } else if (placeSecondDash && s.length < 13) {
                placeSecondDash = false
            }
            if (s.length == Constants.CNIC_LENGTH) {
                var drawable = context.resources.getDrawable(R.drawable.ic_check_circle)
                drawable = DrawableCompat.wrap(drawable!!)
                DrawableCompat.setTint(drawable, context.resources.getColor(R.color.darkgreen_color))
                editText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
            } else {
                editText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }
        }

        fun getAppVersion():String{
            return BuildConfig.VERSION_NAME
            //return "2.0"
        }

        fun pickTime24Hour(): String? {
            return try {
                val d = Date()
                val sdf =
                    SimpleDateFormat(Constants.DATA_TIME_FORMAT_24_HOUR)
                sdf.format(d)
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }

        fun isEmailValid(email: String): Boolean {
            var isValid = false
            try {
                val expression =
                    "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"
                val inputStr: CharSequence = email
                val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
                val matcher = pattern.matcher(inputStr)
                if (matcher.matches()) {
                    isValid = true
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            return isValid
        }

        fun hideKeyboard(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        @Throws(ParseException::class)
        fun isTimeBetweenTwoTime(
            initialTime: String,
            finalTime: String,
            currentTime: String
        ): Boolean {
            val reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$".toRegex()
            return if (initialTime.matches(reg) && finalTime.matches(reg) && currentTime.matches(reg)) {
                var valid = false
                //Start Time
                val inTime = SimpleDateFormat("HH:mm:ss").parse(initialTime)
                val calendar1 = Calendar.getInstance()
                calendar1.time = inTime

                //Current Time
                val checkTime = SimpleDateFormat("HH:mm:ss").parse(currentTime)
                val calendar3 = Calendar.getInstance()
                calendar3.time = checkTime

                //End Time
                val finTime = SimpleDateFormat("HH:mm:ss").parse(finalTime)
                val calendar2 = Calendar.getInstance()
                calendar2.time = finTime
                if (finalTime.compareTo(initialTime) < 0) {
                    calendar2.add(Calendar.DATE, 1)
                    calendar3.add(Calendar.DATE, 1)
                }
                val actualTime = calendar3.time
                if ((actualTime.after(calendar1.time) || actualTime.compareTo(calendar1.time) == 0)
                    && actualTime.before(calendar2.time)
                ) {
                    valid = true
                }
                valid
            } else {
                throw IllegalArgumentException("Not a valid time, expecting HH:MM:SS format")
            }
        }

        fun getCurrentTimeStamp(): String? {
            val d = Date()
            val sdfDate =
                SimpleDateFormat("HH:mm:ss",Locale.ENGLISH) //dd/MM/yyyy
            return sdfDate.format(d)
        }


        fun isTimeAutomatic(c: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Settings.Global.getInt(c.contentResolver, Settings.Global.AUTO_TIME, 0) == 1
            } else {
                Settings.System.getInt(c.contentResolver, Settings.System.AUTO_TIME, 0) == 1
            }
        }
    }
}