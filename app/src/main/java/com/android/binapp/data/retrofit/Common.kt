package com.android.binapp.data.retrofit

object Common {
    private const val BASE_URL = "https://lookup.binlist.net"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient("https://lookup.binlist.net").create(RetrofitServices::class.java)
}