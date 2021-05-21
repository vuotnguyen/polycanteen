package com.example.canteenpoly.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.canteenpoly.R
import com.example.canteenpoly.model.Message1
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.viewtpye_mescan.view.*
import kotlinx.android.synthetic.main.viewtpye_mesuser.view.*

class MesCanAdapter(
    private val listMes: ArrayList<Message1>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType){
            ViewType.TYPE_ONE.type -> {
                val view  = inflater.inflate(R.layout.viewtpye_mesuser,parent,false)
                ViewHolderUser(view)
            }
            else ->{
                val view = inflater.inflate(R.layout.viewtpye_mescan,parent,false)
                ViewHolderCan(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = listMes[position]
        holder.apply {
            when(holder){
                is ViewHolderCan -> {
                    holder.tv_MesCan.text = message.content
                }
                is ViewHolderUser ->{
                    holder.tv_MesUer.text = message.content
                    Glide.with(context).load("https://cdnb.artstation.com/p/assets/images/images/009/836/467/medium/maria-bo-schatzis-stream-profilpicture.jpg?1521139318").into(holder.img_User)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (listMes[position].type){
            0 -> ViewType.TYPE_ONE.type
            else -> ViewType.TYPE_TWO.type
        }
    }

    override fun getItemCount(): Int {
        return listMes.size
    }


}
class ViewHolderCan(view: View):  RecyclerView.ViewHolder(view) {
    val tv_MesCan: TextView
    init {
        tv_MesCan = view.textView22
    }
}
class ViewHolderUser(view: View):  RecyclerView.ViewHolder(view) {
    val tv_MesUer: TextView
    val img_User: CircleImageView
    init {
        tv_MesUer = view.tv_commentUser
        img_User = view.img_avatarCMU
    }
}
enum class ViewType(val type: Int) {
    TYPE_ONE(0),
    TYPE_TWO(1)
}