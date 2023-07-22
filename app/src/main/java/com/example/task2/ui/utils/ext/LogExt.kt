package com.example.task2.ui.utils.ext

import android.util.Log

const val TAG = "logTAG"

// Todo try using Timber
fun logExt(
    message: String
) {
    Log.d(TAG, message)
}