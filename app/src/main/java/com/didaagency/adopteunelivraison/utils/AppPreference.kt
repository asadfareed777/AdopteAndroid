package com.didaagency.adopteunelivraison.utils

import android.content.Context
import android.content.SharedPreferences
import com.didaagency.adopteunelivraison.data.network.response.User
import com.google.gson.Gson

class AppPreference {
    fun clearMemory(context: Context) {
        if (usrPrefrence == null) {
            initPrefrence(context)
        }
        try {
            if (usrPrefrence != null) {
                val editor = usrPrefrence!!.edit()
                editor.clear()
                editor.commit()
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    companion object {
        const val TAG = "AppPreference"
        const val SESSION_NAME = "Sample"
        private var usrPrefrence: SharedPreferences? = null

        private fun initPrefrence(mContext: Context) {
            try {
                usrPrefrence = mContext.getSharedPreferences(
                    SESSION_NAME, Context.MODE_PRIVATE
                )
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }

        fun saveNum(context: Context, value: Long, key: String?) {
            try {
                if (usrPrefrence == null) {
                    initPrefrence(context)
                }
                if (usrPrefrence != null) {
                    val editing = usrPrefrence!!.edit()
                    try {
                        editing.remove(key)
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }
                    editing.putLong(key, value)
                    editing.commit()
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
        fun saveUser(context: Context, value: String?) {
            try {
                if (usrPrefrence == null) {
                    initPrefrence(context)
                }
                if (usrPrefrence != null) {
                    val editing = usrPrefrence!!.edit()
                    try {
                        editing.remove(Constants.KEY_USER)
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }
                    editing.putString(Constants.KEY_USER, value)
                    editing.commit()
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }

        fun getUser(context: Context): User? {
            try {
                if (usrPrefrence == null) {
                    initPrefrence(context)
                }
                return Gson().fromJson(usrPrefrence!!.getString(Constants.KEY_USER, ""), User::class.java)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return null
        }
        fun getNum(context: Context, key: String?): Long {
            try {
                if (usrPrefrence == null) {
                    initPrefrence(context)
                }
                return usrPrefrence!!.getLong(key, -1)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return -1
        }

        fun saveInt(context: Context, value: Int, key: String?) {
            try {
                if (usrPrefrence == null) {
                    initPrefrence(context)
                }
                if (usrPrefrence != null) {
                    val editing = usrPrefrence!!.edit()
                    try {
                        editing.remove(key)
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }
                    editing.putInt(key, value)
                    editing.commit()
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }

        fun getInt(context: Context, key: String?): Int {
            try {
                if (usrPrefrence == null) {
                    initPrefrence(context)
                }
                return usrPrefrence!!.getInt(key, 0)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return -1
        }

        fun saveValue(context: Context, value: String?, key: String?) {
            try {
                if (usrPrefrence == null) {
                    initPrefrence(context)
                }
                if (usrPrefrence != null) {
                    val editing = usrPrefrence!!.edit()
                    try {
                        editing.remove(key)
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }
                    editing.putString(key, value)
                    editing.commit()
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }

        fun getValue(context: Context, key: String?): String? {
            try {
                if (usrPrefrence == null) {
                    initPrefrence(context)
                }
                return usrPrefrence!!.getString(key, null)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return null
        }

        fun saveData(context: Context, values: Boolean, key: String?) {
            try {
                if (usrPrefrence == null) {
                    initPrefrence(context)
                }
                if (usrPrefrence != null) {
                    val editing = usrPrefrence!!.edit()
                    try {
                        editing.remove(key)
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }
                    editing.putBoolean(key, values)
                    editing.commit()
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }

        fun getSavedData(context: Context, key: String?): Boolean {
            var flag = false
            try {
                if (usrPrefrence == null) {
                    initPrefrence(context)
                }
                if (usrPrefrence != null) {
                    flag = usrPrefrence!!.getBoolean(key, false)
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return flag
        }

        fun DeleteSavedData(context: Context?): Boolean {
            try {
                if (usrPrefrence == null) {
                    return true
                }
                if (usrPrefrence != null) {
                    usrPrefrence!!.edit().clear()
                    return true
                }
            } catch (exception: Exception) {
                return false
            }
            return true
        }

        fun DeleteSingleSavedData(context: Context?, Key: String?): Boolean {
            return try {
                if (usrPrefrence == null) {
                    return true
                }
                usrPrefrence!!.edit().remove(Key).commit()
                true
            } catch (exception: Exception) {
                false
            }
        }
    }
}