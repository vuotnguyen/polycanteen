package com.example.canteenpoly.model

class User(){
    lateinit var id: String
    lateinit var avatar: String
    lateinit var nameboss: String
    lateinit var phone: String
    lateinit var nameCanteen: String
    lateinit var address: String
    lateinit var token: String
    lateinit var listChat: ArrayList<String>
    lateinit var listDeal: ArrayList<Deal>
    lateinit var listProduct: ArrayList<Product>

//    constructor(avatar: String,nameboss: String,phone: String,mail: String,nameCanteen:String ,address: String): this(){
//        this.avatar = avatar
//        this.nameboss = nameboss
//        this.phone = phone
//        this.mail = mail
//        this.nameCanteen = nameCanteen
//        this.address = address
//    }

    constructor(
        token: String, id: String, avatar: String, nameboss: String,
        phone: String, nameCanteen: String, address: String,
        listChat: ArrayList<String>, listDeal: ArrayList<Deal>, listProduct: ArrayList<Product>
    ): this(){
        this.id = id
        this.avatar = avatar
        this.nameboss = nameboss
        this.phone = phone
        this.nameCanteen = nameCanteen
        this.address = address
        this.token = token
        this.listChat = listChat
        this.listDeal = listDeal
        this.listProduct = listProduct
    }
    constructor(
        avatar: String, nameboss: String,
        phone: String, nameCanteen: String, address: String,
    ): this(){

        this.avatar = avatar
        this.nameboss = nameboss
        this.phone = phone
        this.nameCanteen = nameCanteen
        this.address = address

    }

    fun toMap(): Map<String,Any> {
        return mapOf(
            "token" to token,
            "id" to id,
            "avatar" to avatar,
            "nameboss" to nameboss,
            "phone" to phone,
            "nameCanteen" to nameCanteen,
            "address" to address,
            "listChat" to listChat,
            "listDeal" to listDeal,
            "listProduct" to listProduct
        )
    }
}

