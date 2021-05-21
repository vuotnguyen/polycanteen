package com.example.canteenpoly.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

class ApiModul {
    private val BASE_URL: String = "https://fcm.googleapis.com/"

    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    fun provideService(retrofit: Retrofit): RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }
}