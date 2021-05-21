package com.example.canteenpoly.model

class Message1 (){
    var type : Int = 0
    lateinit var content: String
    constructor(type: Int, content: String): this(){
        this.type = type
        this.content = content
    }
}