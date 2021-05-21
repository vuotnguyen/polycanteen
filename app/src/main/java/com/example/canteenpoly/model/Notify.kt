package com.example.canteenpoly.model

class Notify(var title: String,var body: String){
    override fun toString(): String {
        return title + body
    }
}