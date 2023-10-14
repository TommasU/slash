package com.bytes.a.half.slash_android

import android.content.Context
import android.widget.Toast

fun Context?.showToast(message : String) {
    this?.let {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}