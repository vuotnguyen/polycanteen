package com.example.canteenpoly.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.canteenpoly.R
import com.example.canteenpoly.adapter.MesCanAdapter
import com.example.canteenpoly.model.Message1
import com.example.canteenpoly.repository.CanteenDAO
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.action_bar_cus.view.*
import kotlinx.android.synthetic.main.fragment_chat_detail.view.*



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatDetailFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatDetailFrag : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var canteenDAO: CanteenDAO
    private lateinit var recyclerView: RecyclerView
    lateinit var mesCanAdapter: MesCanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat_detail, container, false)
        view.textView15.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        view.textView16.text = "Chat detail"
        view.textView17.visibility = View.INVISIBLE
        initView(view)
        return view
    }

    private fun initView(view: View) {
        recyclerView = view.rv_mes
        val keyChat = arguments?.getString("idChat")
        canteenDAO = CanteenDAO()
        canteenDAO.getAllMes(keyChat!!).observe(viewLifecycleOwner, {
            mesCanAdapter = MesCanAdapter(it, requireContext())
            recyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                true
            )
            recyclerView.adapter = mesCanAdapter
        })
        view.img_send.setOnClickListener {
            val mes = view.edt_comment.text.toString()
            val message1 = Message1(1, mes)
            canteenDAO.addMesCan(message1, keyChat)
            view.edt_comment.text = null
            canteenDAO.token(keyChat).observe(viewLifecycleOwner,{
                canteenDAO.sendNotifyUser("c2J46wo_RFi-tFkAlW1isf:APA91bG5EYp5SAEb9lmGWgpjakoVlTO5kShX63bKG_BQH7gAKywHteZaIw4uzuMx9vbFLBBZSeHLny_XDqV6LrF6V0rdnE5Gwa5J6cjgkJ92s-oOoekEvTTyuo_1tLjLEcSjo4dhAfVV")
            })
//
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChatDetailFrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatDetailFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}