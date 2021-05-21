package com.example.canteenpoly.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.canteenpoly.R
import com.example.canteenpoly.adapter.DealAdapter
import com.example.canteenpoly.callBack.ClickDeal
import com.example.canteenpoly.model.Deal
import com.example.canteenpoly.repository.CanteenDAO
import kotlinx.android.synthetic.main.action_bar_cus.view.*
import kotlinx.android.synthetic.main.fragment_order_static.view.*
import kotlin.math.log


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderStaticFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderStaticFrag : Fragment(), ClickDeal,PopupMenu.OnMenuItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var dealAdapter: DealAdapter
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
        val view = inflater.inflate(R.layout.fragment_order_static, container, false)
        view.textView15.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        view.textView16.text = "Deal"
        view.textView17.setBackgroundResource(R.drawable.ic_baseline_search_24)
        view.textView17.setOnClickListener {
//            val popupMenu = PopupMenu(requireContext(), it)
//            val inflater: MenuInflater = popupMenu.menuInflater
//            inflater.inflate(R.menu.statistical_menu,popupMenu.menu)
//
//            popupMenu.show()
            PopupMenu(requireContext(), it).apply {
                // MainActivity implements OnMenuItemClickListener
                setOnMenuItemClickListener(this@OrderStaticFrag)
                inflate(R.menu.statistical_menu)
                show()
            }
        }
        initView(view)
        return view
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId){

            R.id.datHang -> {
                Log.i("TAG", "onMenuItemClick: Đặt hàng")
                true
            }
            R.id.xacNhan -> {
                Log.i("TAG", "onMenuItemClick: Xác Nhận")
                true
            }
            R.id.dangGiao -> {
                Log.i("TAG", "onMenuItemClick: Đang giao")
                true
            }
            R.id.thanhCong -> {
                Log.i("TAG", "onMenuItemClick: Thành công")
                true
            }
            else -> false
        }
    }

    private fun initView(view: View) {
        canteenDAO = CanteenDAO()
        canteenDAO.getAllOrder().observe(viewLifecycleOwner, {
            dealAdapter = DealAdapter(it, requireContext(), this)
            val manager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            view.rv_waitOrder.layoutManager = manager
            view.rv_waitOrder.adapter = dealAdapter
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrderStaticFrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderStaticFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun changeStatus(key: String) {

    }

    override fun gotoConfirm(deal: Deal) {
        val bundle = bundleOf("deal" to deal)
        Navigation.findNavController(requireView()).navigate(
            R.id.action_orderStaticFrag_to_confirmOrder,
            bundle
        )
    }
}