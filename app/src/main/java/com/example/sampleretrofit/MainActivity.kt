package com.example.sampleretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var tvTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tvTitle = findViewById(R.id.tvTitle)


        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val apiInterface : ApiInterface = retrofitBuilder.create(ApiInterface::class.java)


        val call : Call<Model> = apiInterface.getModel(4)

        val allCalls : Call<List<Model>> = apiInterface.getAllPosts("2")


        allCalls.enqueue(object : Callback<List<Model>>{
            override fun onResponse(call: Call<List<Model>>, response: Response<List<Model>>) {
                tvTitle.text = response.body()?.get(0)?.title ?: "Not yet"
            }

            override fun onFailure(call: Call<List<Model>>, t: Throwable) {
                tvTitle.text = t.message
            }
        })

    }
}