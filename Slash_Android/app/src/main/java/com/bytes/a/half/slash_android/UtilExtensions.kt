package com.bytes.a.half.slash_android

import android.content.Context
import android.text.TextUtils
import android.widget.Toast

fun Context?.showToast(message: String) {
    this?.let {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}


fun String?.isValidString(): Boolean {
    return this != null && !TextUtils.isEmpty(this)
}

fun List<Any>?.isValidList(): Boolean {
    return !this.isNullOrEmpty()
}