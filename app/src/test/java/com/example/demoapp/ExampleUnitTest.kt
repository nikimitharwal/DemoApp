package com.example.demoapp

import android.util.Log
import com.example.demoapp.Request.APIService
import com.example.demoapp.models.UserDetailData
import com.example.demoapp.models.UserResult
import com.example.demoapp.models.UsersData
import com.google.gson.Gson
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    lateinit var apiService: APIService
    @Test
    fun Apiuser_Success() {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl(APIService.BASE_URL)
            .build()
        apiService = retrofit.create(APIService::class.java)
        val limit = "100"
        val call = apiService.getUserList(limit)
        try {
            //Magic is here at .execute() instead of .enqueue()
            val response: Response<UserResult?>? = call?.execute()
            var list: UserResult? = response?.body()
            response?.isSuccessful()?.let { assertTrue(it) }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Test
    fun ApiuserDetail_Success() {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl(APIService.BASE_URL)
            .build()
        apiService = retrofit.create(APIService::class.java)

        val call = apiService.getUserDetails("60d0fe4f5311236168a109f7")
        try {
            //Magic is here at .execute() instead of .enqueue()
            val response: Response<UserDetailData?>? = call?.execute()
            var list: UserDetailData? = response?.body()
            if (response != null) {
                assertTrue(response.isSuccessful() && list?.firstName.equals("Jolanda"))
            };
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}