package com.teemodev.commentfacebookui.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager

object DeviceUtil {
    /**
     * Show soft keyboard.
     */
    fun showKeyboard(context: Context) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            0
        )
    }

    /**
     * Hide soft keyboard.
     */
    fun hideKeyboard(context: Context) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(
            InputMethodManager.HIDE_IMPLICIT_ONLY,
            0
        )
    }
}