package com.example.canteenpoly.fragment

import com.example.canteenpoly.App
import com.example.canteenpoly.StorageCommon

abstract class Storage() {
    fun getStorage(): StorageCommon? {
        return App.getInstance().getStorageCommon()
    }
}