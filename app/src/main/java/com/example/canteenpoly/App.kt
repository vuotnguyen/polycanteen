package com.example.canteenpoly

import android.app.Application

class App: Application() {
    private lateinit var storageCommon: StorageCommon
    companion object{
        val instance: App = TODO()
        @JvmName("getInstance1")
        fun getInstance(): App {
            return instance
        }
    }
    override fun onCreate() {
        super.onCreate()
        storageCommon = StorageCommon()
    }
    fun getStorageCommon(): StorageCommon? {
        return storageCommon
    }
}