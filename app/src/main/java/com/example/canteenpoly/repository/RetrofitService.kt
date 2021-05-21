package com.example.canteenpoly.repository

import com.example.canteenpoly.model.NotifyModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitService {
    @Headers("Authorization: key=AAAAU1NOJeg:APA91bFSkzSYoJKwKoXrZRJcW_yMdghGaHFrYxKQLL_jd5yaInmbiOdxtlWaOcyD2tvSPPZNhFQNRj4-uKsmlH4ERghSvynLvmjGTR-Ol8MmNwEyZuCQdwXol6ZnDm7wSiIlqN9lbOcT","Content-Type:application/json")
    @POST("fcm/send")
    fun sendNotifycation (@Body notifyModel: NotifyModel): Call<NotifyModel>
}