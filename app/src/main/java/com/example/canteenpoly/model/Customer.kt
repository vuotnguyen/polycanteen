package com.example.canteenpoly.model

class Customer() {
    lateinit var id: String
    lateinit var imageURI: String
    lateinit var name: String
    lateinit var token: String
    lateinit var mes: ArrayList<String>

    constructor( id: String,  imageURI: String, name: String,  token: String, mes: ArrayList<String>): this(){
        this.id = id
        this.imageURI = imageURI
        this.name = name
        this.token = token
        this.mes = mes
    }
    fun toMap(): Map<String,Any> {
        return mapOf(
            "id" to id,
            "imageURI" to imageURI,
            "name" to name,
            "token" to token,
            "mes" to mes,
        )
    }
}