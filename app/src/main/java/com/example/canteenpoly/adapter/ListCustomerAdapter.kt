package com.example.canteenpoly.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.canteenpoly.R
import com.example.canteenpoly.callBack.ClickItem
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.listcus_adapter.view.*

class ListCustomerAdapter (private val listCus: ArrayList<String>, private val context: Context, private val even: ClickItem):
    RecyclerView.Adapter<ListCustomerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCustomerAdapter.ViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.listcus_adapter, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListCustomerAdapter.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener { even.gotoDetail(listCus[position]) }
//        holder.tvLastMes.text = listCus.
    }

    override fun getItemCount(): Int {
        return listCus.size
    }
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageView: CircleImageView
        val tvName: TextView
        val tvLastMes: TextView
        init {
            imageView = view.circleImageView
            tvName = view.textView21
            tvLastMes = view.textView20
        }
    }
}