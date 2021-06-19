package com.example.animallist.global.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.DisplayMetrics

fun Context?.isNetworkAvailable(): Boolean =
    this?.let {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val netCapabilities =
                connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                netCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                netCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            val netInfo = connectivityManager.activeNetworkInfo ?: return false
            return netInfo.isConnected
        }
    } ?: run {
        return false
    }

fun Context.dpToPx(dp: Float) =
    dp * (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)

fun Context.pxToDp(px: Float) =
    px / (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)