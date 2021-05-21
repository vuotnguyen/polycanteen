package com.example.canteenpoly.model

class Chat() {
    lateinit var message1: ArrayList<Message1>
    lateinit var nameCus: String
    lateinit var tokenCustomer: String
    lateinit var tokenUser: String
    lateinit var urlCus: String
    lateinit var key: String

    constructor( message1: ArrayList<Message1>,  nameCus: String, tokenCustomer: String,  tokenUser: String, urlCus: String, key: String): this(){
        this.message1 = message1

        this.nameCus = nameCus
        this.tokenCustomer = tokenCustomer
        this.tokenUser = tokenUser
        this.urlCus = urlCus
        this.key = key
    }
}