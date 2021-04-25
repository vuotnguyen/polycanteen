package com.example.canteenpoly.model

import kotlin.collections.ArrayList

class Message (){
    lateinit var customer: Customer
    lateinit var listMessage: ArrayList<String>
    constructor(customer: Customer, listMessage: ArrayList<String>): this(){
        this.customer = customer
        this.listMessage = listMessage
    }
}