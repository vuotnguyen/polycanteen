package com.example.canteenpoly.callBack

import com.example.canteenpoly.model.Product

interface BackListProduct {
    fun updateProduct(product: Product)
    fun deleteProduct(key: String): Boolean
}