package com.example.canteenpoly.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.FileInputStream
import kotlin.math.log

class FireStoreApp: ViewModel() {
    var fireStorage  = FirebaseStorage.getInstance()
    var ref = fireStorage.reference

    fun upFile(path: String){
        val file = Uri.fromFile(File(path))
        val riversRef = ref.child("images/avatar")
        val uploadTask = riversRef.putFile(file)

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
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