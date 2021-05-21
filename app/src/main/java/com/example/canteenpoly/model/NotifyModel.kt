package com.example.canteenpoly.model
import com.google.gson.annotations.SerializedName
class NotifyModel {
    @SerializedName("to")
    lateinit var token: String
    @SerializedName("data")
    lateinit var notify: Notify

    constructor(token: String,notify: Notify) : this(){
        this.token = token
        this.notify = notify
    }
    constructor()
}