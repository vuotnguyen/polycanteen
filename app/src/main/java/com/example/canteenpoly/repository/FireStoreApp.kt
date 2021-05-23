package com.example.canteenpoly.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.FileInputStream
import kotlin.math.log

class FireStoreApp: ViewModel() {
    var fireStorage  = FirebaseStorage.getInstance()
    var ref = fireStorage.reference

    fun upFile(path: String, type: Int): String{
        val file = Uri.fromFile(File(path))
        var path = ""
        var riversRef : StorageReference? = null
        if(type ==0){
            riversRef = ref.child("images/avatar")
            path = "images/avatar"
        }
        if(type == 1){
            riversRef = ref.child("images/product/${file.lastPathSegment}")
            path = "images/product/${file.lastPathSegment}"
        }
        val uploadTask = riversRef?.putFile(file)

// Register observers to listen for when the download is done or if it fails
        uploadTask?.addOnFailureListener {
            // Handle unsuccessful uploads
        }?.addOnSuccessListener { taskSnapshot ->
            val taskUri = taskSnapshot.storage.downloadUrl
            if(taskUri.isSuccessful){
                val rs = taskUri.result.toString()
                Log.i("TAG", "upFile: "+ rs)
            }
        }
        return path
    }

    fun downloadFile(path: String): MutableLiveData<String>{
        val rs = MutableLiveData<String>()
        ref.child(path).downloadUrl.addOnSuccessListener {
            rs.postValue(it.toString())
            Log.i("TAG", "downloadFile: "+ it.toString())
        }.addOnFailureListener {
            // Handle any errors
        }
        return rs
    }

}