package com.example.canteenpoly.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.canteenpoly.R
import com.example.canteenpoly.callBack.BackListImg
import kotlinx.android.synthetic.main.img_adapter.view.*

class ListImgAdaper (private val listImg: ArrayList<String>,private val context: Context,private val even: BackListImg):
    RecyclerView.Adapter<ListImgAdaper.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListImgAdaper.ViewHolder {
       val view  = LayoutInflater.from(parent.context).inflate(R.layout.img_adapter, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListImgAdaper.ViewHolder, position: Int) {
        Glide.with(context).load(listImg[position]).into(holder.imageView)
        holder.imageView.setOnClickListener { even.sendPath(listImg[position]) }
    }

    override fun getItemCount(): Int {
        return listImg.size
    }
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageView: ImageView
        init {
            imageView = view.imageView8
        }

    }

}