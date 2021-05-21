package com.example.canteenpoly.callBack

import com.example.canteenpoly.model.Deal

interface ClickDeal {
    fun changeStatus(key: String)
    fun gotoConfirm(deal: Deal)
}
