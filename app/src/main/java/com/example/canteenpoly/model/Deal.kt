package com.example.canteenpoly.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

class Deal(): Parcelable {
    lateinit var adress: String
    lateinit var key: String
    lateinit var cash: String
    lateinit var id: String
    lateinit var image: String
    lateinit var name: String
    lateinit var note: String
    lateinit  var price: String
    lateinit var soLuong: String
    lateinit var status: String

    constructor(parcel: Parcel) : this() {
        adress = parcel.readString().toString()
        key = parcel.readString().toString()
        cash = parcel.readString().toString()
        id = parcel.readString().toString()
        image = parcel.readString().toString()
        name = parcel.readString().toString()
        note = parcel.readString().toString()
        price = parcel.readString().toString()
        soLuong = parcel.readString().toString()
        status = parcel.readString().toString()
    }

//    constructor(
//        adress: String,
//        key: String,
//        cash: String,
//        id_user: String,
//        image: String,
//        name: String,
//        note: String,
//        price: String,
//        status: String
//    ) : this() {
//        this.adress = adress
//        this.key = key
//        this.cash = cash
//        this.id_user = id_user
//        this.image = image
//        this.name = name
//        this.note = note
//        this.price = price
//        this.status = status
//    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(adress)
        parcel.writeString(key)
        parcel.writeString(cash)
        parcel.writeString(id)
        parcel.writeString(image)
        parcel.writeString(name)
        parcel.writeString(note)
        parcel.writeString(soLuong)
        parcel.writeString(price)
        parcel.writeString(status)
    }

    companion object CREATOR : Parcelable.Creator<Deal> {
        override fun createFromParcel(parcel: Parcel): Deal {
            return Deal(parcel)
        }

        override fun newArray(size: Int): Array<Deal?> {
            return arrayOfNulls(size)
        }
    }
}