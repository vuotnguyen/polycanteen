package com.example.canteenpoly.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.canteenpoly.R
import com.example.canteenpoly.adapter.ListCustomerAdapter
import com.example.canteenpoly.callBack.ClickItem
import com.example.canteenpoly.model.Customer
import com.example.canteenpoly.model.Message
import com.example.canteenpoly.repository.CanteenDAO
import kotlinx.android.synthetic.main.action_bar_cus.view.*
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_chat.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatFrag : Fragment(),ClickItem {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var canteenDAO: CanteenDAO
    lateinit var rvCus : RecyclerView
    lateinit var listCustomerAdapter: ListCustomerAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

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
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_chat, container, false)
        view.textView15.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        view.textView16.text = "Chat"
        view.textView17.visibility = View.INVISIBLE

        initView(view)
        return view
    }

    private fun initView(view: View) {
        linearLayoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        rvCus = view.rv_listCustomer
        canteenDAO = CanteenDAO()
        canteenDAO.getListCustomer(HomeFrag.uid).observe(viewLifecycleOwner,{
            listCustomerAdapter = ListCustomerAdapter(it,requireContext(),this)
            rvCus.layoutManager = linearLayoutManager
            rvCus.adapter = listCustomerAdapter
        })
    }
    override fun gotoDetail(idChat: String) {
        Log.i("TAG", "gotoDetail: "+ "goto detail")
        val bundle = bundleOf("idChat" to idChat)
        Navigation.findNavController(requireView()).navigate(R.id.action_chatFrag_to_chatDetailFrag, bundle)

    }

    private fun addChat() {
//        canteenDAO = CanteenDAO()
//        val customer = Customer("sBRcetOQU2hb1Fc8ktbHvGF7Nt42","default","Nguyen ngoc anh",
//            "cFySA7zWSO2O6MCWqY95Bn:APA91bHkKbhkxARUgZccnRNr6RDstojTWfLf-oeEbNJeJH7QSbBIaYTLAjB0S-6CE96oAviaKjJ-yVPqlYs1P3cXVUhPPSS7gfhBBBvUbQgkq6JOyB6qGUVrzHfmjpn5yOgpbiUdfRdu",
//        )
//
//        var listMes: ArrayList<String> = ArrayList()
//
//        var message = Message(customer,listMes)
//        canteenDAO.addChat(HomeFrag.uid)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChatFrag.
         */

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}