package com.example.canteenpoly.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.canteenpoly.R
import com.example.canteenpoly.callBack.ClickItem
import com.example.canteenpoly.model.Chat
import com.example.canteenpoly.repository.CanteenDAO
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.listcus_adapter.view.*

class ListCustomerAdapter (
    private val listCus: ArrayList<Chat>,
    private val context: Context,
    private val even: ClickItem):
    RecyclerView.Adapter<ListCustomerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCustomerAdapter.ViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.listcus_adapter, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListCustomerAdapter.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener { even.gotoDetail(listCus[position].key) }

        holder.viewNoty.visibility = even.checkNoty(listCus[position].key)
    }

    override fun getItemCount(): Int {
        return listCus.size
    }
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageView: CircleImageView
        val tvName: TextView
        val tvLastMes: TextView
        val viewNoty: View
        init {
            imageView = view.circleImageView
            tvName = view.textView21
            tvLastMes = view.textView20
            viewNoty = view.viewNoti
        }
    }
}