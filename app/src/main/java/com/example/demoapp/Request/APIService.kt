package com.example.demoapp.Request

import com.example.demoapp.models.UserDetailData
import com.example.demoapp.models.UserResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("user")
    @Headers("app-id:60d324bd5247dd501256c284")
    fun getUserList(@Query("limit") page: String?): Call<UserResult?>?

    @GET("user/{userId}")
    @Headers("app-id:60d324bd5247dd501256c284")
    fun getUserDetails(
        @Path(
            value = "userId",
            encoded = true
        ) userId: String?
    ): Call<UserDetailData?>?

    companion object {
        const val BASE_URL = "https://dummyapi.io/data/api/"
    }
}