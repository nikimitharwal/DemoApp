package com.example.demoapp.UI

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.demoapp.R
import com.example.demoapp.Request.APIService
import com.example.demoapp.Utills.setupCache
import com.example.demoapp.models.UserDetailData
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView
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

class UserDetail : AppCompatActivity() {
   lateinit var apiService: APIService
    lateinit var userId: String
    lateinit var tvname: TextView
    lateinit var tvemail: TextView
    lateinit var tvphone: TextView
    lateinit var tvdate: TextView
    lateinit var tvgender: TextView
    lateinit var userimg: CircleImageView
    lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_detail)
        initview()
    }

    private fun initview() {
        tvgender = findViewById(R.id.gender)
        tvname = findViewById(R.id.name)
        tvemail = findViewById(R.id.email)
        tvphone = findViewById(R.id.phone)
        tvdate = findViewById(R.id.birthday)
        userimg = findViewById(R.id.user_img)
        toolbar = findViewById(R.id.toolbar_common)
        toolbar = findViewById(R.id.toolbar_common)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle("DemoApp")
            toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"))
        }
        val i = intent
        userId = i.getStringExtra("id").toString()
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
        val call = apiService.getUserDetails(userId)
        call?.enqueue(object : Callback<UserDetailData?> {
            override fun onResponse(
                call: Call<UserDetailData?>,
                response: Response<UserDetailData?>
            ) {
                if (response.body() != null) {
                    tvname.text = String.format(
                        "%s %s %s",
                        response.body()!!.title,
                        response.body()!!.firstName,
                        response.body()!!.lastName
                    )
                    tvemail.text = response.body()!!.email
                    tvphone.text = response.body()!!.phone
                    tvgender.text = response.body()!!.gender
                    tvdate.text = response.body()!!.dateOfBirth
                    Glide.with(this@UserDetail)
                        .load(response.body()!!.picture)
                        .into(userimg)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Please enable your internet connection",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(
                call: Call<UserDetailData?>,
                t: Throwable
            ) {
                Toast.makeText(this@UserDetail, "Failure", Toast.LENGTH_SHORT).show()
            }
        })
    }
}