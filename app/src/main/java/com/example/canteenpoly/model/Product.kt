package com.example.canteenpoly.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.versionedparcelable.ParcelField


@Keep class Product(): Parcelable {
    lateinit var key : String
    var avatarP: String = ""
    lateinit var nameP: String
    lateinit var donVi: String
    var price: Int = 0
    lateinit var type: String
    lateinit var detail: String

    constructor(parcel: Parcel) : this() {
        key = parcel.readString().toString()
        avatarP = parcel.readString().toString()
        nameP = parcel.readString().toString()
        donVi = parcel.readString().toString()
        price = parcel.readInt()
        type = parcel.readString().toString()
        detail = parcel.readString().toString()
    }

    constructor(key: String, avatarP: String,  nameP: String,  donVi: String,  price: Int,  type: String,  detail: String): this(){
        this.key = key
        this.avatarP = avatarP
        this.nameP = nameP
        this.donVi = donVi
        this.price = price
        this.type = type
        this.detail = detail
    }
    fun toMap(): Map<String,Any> {
        return mapOf(
            "key" to key,
            "avatarP" to avatarP,
            "nameP" to nameP,
            "donVi" to donVi,
            "price" to price,
            "type" to type,
            "detail" to detail

        )
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeString(avatarP)
        parcel.writeString(nameP)
        parcel.writeString(donVi)
        parcel.writeInt(price)
        parcel.writeString(type)
        parcel.writeString(detail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}