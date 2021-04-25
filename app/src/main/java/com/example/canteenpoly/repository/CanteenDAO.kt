package com.example.canteenpoly.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.canteenpoly.model.Message
import com.example.canteenpoly.model.Product
import com.example.canteenpoly.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CanteenDAO : ViewModel() {
    private var database = FirebaseDatabase.getInstance()
    private val myref = database.getReference("Canteen")
    lateinit var userLiveData: MutableLiveData<User>
    lateinit var listProduct: MutableLiveData<ArrayList<Product>>
    lateinit var listPro: ArrayList<Product>

    fun addUser(user: User, uid: String, listProduct: ArrayList<Product>) {
        myref.child(uid).setValue(user)
        myref.child(uid).child("product").setValue(listProduct)
    }

    fun getUser(key: String): MutableLiveData<User> {
        userLiveData = MutableLiveData()
        myref.child(key).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val img = snapshot.child("avatar").value.toString()
                val nameCan = snapshot.child("nameCanteen").value.toString()
                val nameBoss = snapshot.child("nameboss").value.toString()
                val phone = snapshot.child("phone").value.toString()
                val address = snapshot.child("address").value.toString()
                userLiveData.postValue(User(img,nameBoss,phone,nameCan,address))
                Log.i("TAG", "onDataChange: " + snapshot.child("listProduct"))
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "loadPost:onCancelled", error.toException())
            }
        })
        return userLiveData
    }

    fun upDateuser(user: User, key: String) {
        val postValue = user.toMap()
        val updateChild = hashMapOf<String, Any>(
            key to postValue
        )
        myref.updateChildren(updateChild)
        Log.i("TAG", "upDateuser: " )
    }
    fun addProduct(product: Product,uid: String){
        myref.child(uid).child("listProduct").push().setValue(product)
    }
    fun getAllProduct(uid: String): MutableLiveData<ArrayList<Product>>{
        listProduct = MutableLiveData()
        myref.child(uid).child("listProduct").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listPro = ArrayList()
                snapshot.children.forEach {
                    val product = it.getValue(Product::class.java)
                    product?.key = it.key.toString()
                    listPro.add(0, product!!)
                }
                listProduct.postValue(listPro)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return listProduct
    }

    fun updateProduct(product: Product, key: String) {
        val postValue = product.toMap()
        val upDateProduct = hashMapOf<String,Any>(product.key to postValue)
        myref.child(key).child("listProduct").updateChildren(upDateProduct)
    }

    fun deleteProduct(key: String, uid: String):Boolean {
        myref.child(uid).child("product").child(key).removeValue()
        return  true
    }
    fun addChat(key: String, message: Message){
        myref.child(key).child("chats").push().setValue(message)
    }

}