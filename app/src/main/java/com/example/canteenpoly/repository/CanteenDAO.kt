package com.example.canteenpoly.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.canteenpoly.model.*
import com.google.firebase.database.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

class CanteenDAO : ViewModel() {
    private var database = FirebaseDatabase.getInstance()
    private val myref = database.getReference("Canteen")
    private val myrefChat = database.getReference("Chats")
    private val myrefProduct = database.getReference("Product")
    private val myrefOder = database.getReference("Order")
    lateinit var userLiveData: MutableLiveData<User>
    lateinit var listProduct: MutableLiveData<ArrayList<Product>>
    lateinit var listPro: ArrayList<Product>

    lateinit var listChatLive: MutableLiveData<ArrayList<Chat>>
    lateinit var listChat: ArrayList<Chat>

    lateinit var listMesLive: MutableLiveData<ArrayList<Message1>>
    lateinit var listMes: ArrayList<Message1>

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
        myrefProduct.push().setValue(product)
    }
    fun getAllProduct(uid: String): MutableLiveData<ArrayList<Product>>{
        listProduct = MutableLiveData()
        myrefProduct.addValueEventListener(object : ValueEventListener {
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
        myrefProduct.updateChildren(upDateProduct)
    }

    fun deleteProduct(key: String):Boolean {
        myrefProduct.child(key).removeValue()
        return  true
    }

    fun getListCustomer(id: String): MutableLiveData<ArrayList<Chat>>{
        listChatLive = MutableLiveData()
        listChat = ArrayList()
        myrefChat.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    Log.i("TAG", "onDataChange: "+it.toString())
                    listChat.add(it.getValue(Chat::class.java)!!)
                }
                listChatLive.postValue(listChat)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return listChatLive
    }

    fun getAllMes(idChat: String):MutableLiveData<ArrayList<Message1>> {
        listMesLive = MutableLiveData()
        listMes = ArrayList()
        myrefChat.child(idChat).child("listMes").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listMes.clear()
                snapshot.children.forEach {
                    listMes.add(0,it.getValue(Message1::class.java)!!)
                }
                listMesLive.postValue(listMes)
                Log.i("TAG", "getAllMes: "+ listMes.size)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return listMesLive
    }
    fun checkNotyfi(idChat: String):MutableLiveData<Boolean> {
        val rs = MutableLiveData<Boolean>()
//        rs.postValue(false)
        myrefChat.child(idChat).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.i("TAG", "onChildAdded: "+ snapshot.toString())
//                val mes = snapshot.getValue(Message1::class.java)
//                if (mes!!.type == 1) {
//                    Log.i("TAG", "onChildAdded: " + mes.type)
//                    rs.postValue(true)
//                } else rs.postValue(false)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        return rs
    }

    fun addMesCan(mes: Message1, keyChat: String) {
        myrefChat.child(keyChat).child("listMes").push().setValue(mes)
    }
    fun token(keyChat: String):MutableLiveData<String> {
        val token = MutableLiveData<String>()
        myrefChat.child(keyChat).child("tokenCustomer").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val a: String = snapshot.value.toString()
//                token.value = a
                token.postValue(a)
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        return token
    }
    fun sendNotifyUser(token: String) {
        Log.i("TAG", "sendNotifyUser: "+ token)
        val notifyModel = NotifyModel(token, Notify("Thông báo", "PolyCanteen đã trả lời tin nhắn của bạn"))
        val userSend : Call<NotifyModel> = provideService(provideRetrofit()).sendNotifycation(notifyModel)
        userSend.enqueue(object : Callback<NotifyModel> {
            override fun onResponse(call: Call<NotifyModel>, response: Response<NotifyModel>) {
                Log.i("TAG", "onResponse: "+ response.isSuccessful)
            }
            override fun onFailure(call: Call<NotifyModel>, t: Throwable) {

            }
        })
    }
    fun getAllOrder(): MutableLiveData<ArrayList<Deal>>{
        val listOderLive = MutableLiveData<ArrayList<Deal>>()
        val listOder = ArrayList<Deal>()
        myrefOder.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val deal = it.getValue(Deal::class.java)
                    listOder.add(0, deal!!)
                }
                listOderLive.postValue(listOder)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        return listOderLive
    }

    fun changStatus(key: String) {
        myrefOder.child(key).child("status").setValue("Đã nhận")
    }

    fun upAvatarCanteen(it: String, uid: String) {
        myref.child(uid).child("avatar").setValue(it)
    }

}