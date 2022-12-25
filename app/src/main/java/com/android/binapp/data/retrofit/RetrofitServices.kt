package com.android.binapp.data.retrofit


import com.android.binapp.domain.models.BinInfoEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface RetrofitServices {
  //  @Headers("Content-Type:application/json")

    /*@GET("{bin}")
    fun getBinInfo(
        @Path("bin") binNumber:Int
    ): Call<BinResponse>


*/
    @Headers("Content-Type:application/json")
    @GET("{bin}")
    fun getBinInfo(
        @Path("bin") binNumber:Int
    ): Call<BinResponse>
}