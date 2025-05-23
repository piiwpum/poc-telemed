package com.pahnupan.poc.agora.data.helper

import android.util.Log
import com.pahnupan.poc.agora.core.SharePref
import okhttp3.Interceptor
import okhttp3.Response


class HeaderInterceptor(
    private val sharePref: SharePref
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        Log.d("rere" , "my au = ${sharePref.token}")
        sharePref.token?.let {
            request.addHeader("Authorization","Bearer ${it}")

        }
        return chain.proceed(request.build())
    }

}