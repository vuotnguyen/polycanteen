package com.example.canteenpoly.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.canteenpoly.R
import com.example.canteenpoly.adapter.ListProductAdapter
import com.example.canteenpoly.callBack.BackListProduct
import com.example.canteenpoly.model.Product
import com.example.canteenpoly.repository.CanteenDAO
import kotlinx.android.synthetic.main.action_bar_cus.view.*
import kotlinx.android.synthetic.main.fragment_product.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductFrag : Fragment(),BackListProduct {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var uid: String
    lateinit var listProductAdapter: ListProductAdapter
    lateinit var canteenDAO: CanteenDAO


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
        val view =  inflater.inflate(R.layout.fragment_product, container, false)

        initView(view)
        return view
    }

    private fun initView(view: View) {
        view.textView15.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        view.textView17.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_productFrag_to_addProductFrag,arguments) }
        uid = arguments?.getString("uid").toString()

        canteenDAO = CanteenDAO()

        canteenDAO.getAllProduct(uid).observe(viewLifecycleOwner, {
            listProduct = it
            listProductAdapter = ListProductAdapter(it, requireContext(), this)
            val manager =
                GridLayoutManager(requireContext(), 3, LinearLayoutManager.VERTICAL, false)
            view.rv_listProduct.layoutManager = manager
            view.rv_listProduct.adapter = listProductAdapter

        })
    }

    override fun onResume() {
        super.onResume()

    }
    companion object {
        var listProduct: ArrayList<Product> =  ArrayList()
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductFrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun updateProduct(product: Product) {
        val bundle = Bundle()
        bundle.putParcelable("product", product)
        bundle.putString("uid", uid)
        Navigation.findNavController(requireView()).navigate(R.id.action_productFrag_to_addProductFrag, bundle)
    }

    override fun deleteProduct(key: String): Boolean {
        return canteenDAO.deleteProduct(key,uid)
    }
}