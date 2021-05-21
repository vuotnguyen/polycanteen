package com.example.canteenpoly.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.canteenpoly.R
import com.example.canteenpoly.model.Deal
import com.example.canteenpoly.repository.CanteenDAO
import kotlinx.android.synthetic.main.action_bar_cus.view.*
import kotlinx.android.synthetic.main.fragment_confirm_order.view.*


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ConfirmOrder.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConfirmOrder : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var canteenDAO: CanteenDAO

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
       val view = inflater.inflate(R.layout.fragment_confirm_order, container, false)
        view.textView15.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        view.textView16.text = "Deal detail"
        view.textView17.visibility = View.INVISIBLE
        initView(view)
        return  view
    }

    private fun initView(view: View) {
        canteenDAO = CanteenDAO()
        val deal: Deal = arguments?.get("deal") as Deal
        Log.i("TAG", "initView: "+ deal.id)
        view.textView27.text = "Nguyễn Văn Vượt"
        view.textView28.text = "Món ăn: "+ deal.name
        view.textView29.text = "Số lượng: "+ deal.soLuong
        view.textView30.text = "Đơn giá: 30000" + " vnđ"
        view.textView32.text = deal.price + " vnđ"
        Glide.with(requireContext()).load(deal.image).into(view.imageView12)
        view.button6.setOnClickListener {
            canteenDAO.changStatus(deal.key)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ConfirmOrder.
         */

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ConfirmOrder().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}