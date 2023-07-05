package com.didaagency.adopteunelivraison.utils

import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.Spanned
import android.text.TextWatcher
import android.widget.EditText

/**
 * Created by Asad Fareed on 11/23/2022
 */

class TextWatcherCnic(private val editText: EditText) : TextWatcher {
    private var previousText: String? = null
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        try {
            if (previousText != null && previousText!!.length > s.toString().length && editText.selectionEnd != s.length) {
                editText.setText(previousText)
                editText.setSelection(previousText!!.length)
            } else {
                val textFormatted = editText.text.toString().trim { it <= ' ' }
                val textOriginal = textFormatted.replace("-".toRegex(), "").trim { it <= ' ' }
                if (textOriginal.length >= 13 && (textFormatted.length <= textOriginal.length || textFormatted[13] != '-')) {
                    editText.setText(
                        textOriginal.substring(0, 5) + "-" + textOriginal.substring(
                            5,
                            12
                        ) + "-" + textOriginal.substring(12)
                    )
                    editText.setSelection(editText.text.toString().length)
                } else if (textOriginal.length >= 6 && textFormatted[5] != '-') {
                    editText.setText(textOriginal.substring(0, 5) + "-" + textOriginal.substring(5))
                    editText.setSelection(editText.text.toString().length)
                }
                previousText = editText.text.toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun afterTextChanged(s: Editable) {}

    init {
        //		this.editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        val inputFilterNumber =
            InputFilter { source: CharSequence, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int ->
                var i = start
                while (i < end) {
                    if (!(Character.isDigit(source[i]) || source[i] == '-')) {
                        return@InputFilter ""
                    }
                    i++
                }
                null
            }
        editText.filters = arrayOf(inputFilterNumber, LengthFilter(15))
    }
}