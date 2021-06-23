package com.example.demoapp.UI

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapp.Adapters.UserListAdapter
import com.example.demoapp.R
import com.example.demoapp.Request.APIService
import com.example.demoapp.Utills.setupCache
import com.example.demoapp.models.UserResult
import com.example.demoapp.models.UsersData
import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*

class Dashboard : AppCompatActivity() {
   lateinit var apiService: APIService
    lateinit  var recyclerView: RecyclerView
    lateinit var list: List<UsersData>
    lateinit var adapter: UserListAdapter
    lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycle_view)
        toolbar = findViewById(R.id.toolbar_common)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle("DemoApp")
            toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"))
        }
        recyclerView.setLayoutManager(LinearLayoutManager(this@Dashboard))
        setupRetrofitAndOkHttp()
    }

    private fun setupRetrofitAndOkHttp() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpCacheDirectory = File(cacheDir, "offlineCache")
        //10 MB
        val cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)
        val httpClient = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(setupCache.provideCacheInterceptor())
            .addInterceptor(setupCache.provideOfflineCacheInterceptor())
            .build()
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(httpClient)
            .baseUrl(APIService.BASE_URL)
            .build()
        apiService = retrofit.create(APIService::class.java)
        val limit = "100"
        val call = apiService.getUserList(limit)
        call?.enqueue(object : Callback<UserResult?> {
            override fun onResponse(
                call: Call<UserResult?>,
                response: Response<UserResult?>
            ) {
                if (response.body() != null) {
                list = ArrayList()
                list = response.body()!!.usersData!!
                adapter = list.let { UserListAdapter(this@Dashboard, it) }
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }}

            override fun onFailure(
                call: Call<UserResult?>,
                t: Throwable
            ) {
                Toast.makeText(this@Dashboard, "Failure", Toast.LENGTH_SHORT).show()
            }
        })
    }
}