package com.example.canteenpoly.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.canteenpoly.R
import com.example.canteenpoly.callBack.BackListProduct

import com.example.canteenpoly.model.Product

import kotlinx.android.synthetic.main.product_adapter.view.*

class ListProductAdapter(
    private val listProduct: ArrayList<Product>,
    private val context: Context,
    private val even: BackListProduct
) :
    RecyclerView.Adapter<ListProductAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.product_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(listProduct[position].avatarP).into(holder.imageView)
        holder.tvName.text = listProduct[position].nameP
        holder.tvPrice.text = listProduct[position].price.toString()
        holder.itemView.setOnClickListener { even.updateProduct(listProduct[position]) }
        holder.itemView.setOnLongClickListener { even.deleteProduct(listProduct[position].key) }
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView
        val tvName: TextView
        val tvPrice: TextView

        init {
            imageView = view.imageView9
            tvName = view.textView9
            tvPrice = view.textView18
        }
    }
}