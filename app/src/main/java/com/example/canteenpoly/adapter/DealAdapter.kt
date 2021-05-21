package com.example.canteenpoly.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.canteenpoly.R
import com.example.canteenpoly.callBack.ClickDeal
import com.example.canteenpoly.model.Deal
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.listdeal_adapter.view.*

class DealAdapter (private val listDeal: ArrayList<Deal>, private val context: Context, private val even: ClickDeal):
    RecyclerView.Adapter<DealAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealAdapter.ViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.listdeal_adapter, parent,false)
        return ViewHolder(view)
    }
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deal = listDeal[position]
        Glide.with(context).load(R.drawable.avatarmacdinh).into(holder.imageView)
        holder.tvCash.text = deal.cash
        holder.tvCash.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_style_24,0,0,0)
        holder.tvName.text = deal.name
        holder.tvNote.text = deal.note
        holder.tvNote.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_edit_24,0,0,0)
        holder.tvPrice.text = deal.price
        holder.tvStatus.text = deal.status
        holder.tvStatus.setOnClickListener {
            even.changeStatus(deal.key)
        }
        holder.itemView.setOnClickListener {
            even.gotoConfirm(deal)
        }
    }

    override fun getItemCount(): Int {
        return listDeal.size
    }
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageView: CircleImageView
        val tvName: TextView
        val tvCash: TextView
        val tvNote: TextView
        val tvPrice: TextView
        val tvStatus: TextView
        init {
            imageView = view.imageView11
            tvName = view.textView10
            tvCash = view.textView23
            tvPrice = view.textView14
            tvNote = view.textView24

            tvStatus = view.textView25
        }
    }


}