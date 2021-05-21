package com.example.canteenpoly.callBack

interface ClickItem {
   fun gotoDetail(idChat: String)
   fun checkNoty(idChat: String): Int
}