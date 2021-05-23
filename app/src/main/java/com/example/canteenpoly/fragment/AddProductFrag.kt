package com.example.canteenpoly.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.canteenpoly.R
import com.example.canteenpoly.model.Product
import com.example.canteenpoly.repository.CanteenDAO
import com.example.canteenpoly.repository.FireStoreApp
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.android.synthetic.main.fragment_add_product.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddProductFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddProductFrag : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var product = Product()
    private lateinit var listProduct: ArrayList<Product>
    private var canteenDAO = CanteenDAO()
    private var fireStoreApp = FireStoreApp()
    lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("key")?.observe(
            viewLifecycleOwner
        ) { result ->
            product.avatarP = result
            Glide.with(view).load(result).into(view.imageView5)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_product, container, false)

        initView(view)
        return view
    }

    private fun initView(view: View) {
        uid = arguments?.getString("uid").toString()
        if(arguments?.getParcelable<Product>("product") ==null){
            view.button3.visibility = View.VISIBLE
        }else{
            product = arguments?.getParcelable("product")!!
            uid = arguments?.getString("uid")!!

            view.button4.visibility = View.VISIBLE
            Glide.with(view).load(product.avatarP).into(view.imageView5)
            view.edt_nameP.setText(product.nameP)
            view.editTextTextPersonName2.setText(product.price.toString())
            view.edt_detail.setText(product.detail)
        }
        listProduct = ArrayList()
        //create spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.donvi_array,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            view.spinner.adapter = it

        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.type_array,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            view.spinner2.adapter = it
        }
        val bundle = bundleOf("type" to 1)
        view.tv_chooseimage.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_addProductFrag_to_listImgFrag,bundle)
        }
        view.button3.setOnClickListener {
            if(product.avatarP == ""){
                product.avatarP = "default"
            }

            when(""){
                edt_nameP.text.toString(),
                editTextTextPersonName2.text.toString(),
                edt_detail.text.toString()-> Toast.makeText(context,"Nhap vao de",Toast.LENGTH_LONG).show()
                else -> {
                    Log.i("TAG", "initView: "+uid)
                    product.key = ""
                    fireStoreApp.downloadFile("images/avatar")
                        .observe(viewLifecycleOwner, {
                            product.avatarP = it
                            listProduct.add(product)
                            canteenDAO.addProduct(product,uid)
                        })

                    Toast.makeText(requireContext(),"Thêm thành công", Toast.LENGTH_SHORT).show()
                }
            }
        }
        view.button4.setOnClickListener {
            fireStoreApp.downloadFile("images/avatar")
                .observe(viewLifecycleOwner, {
                    product.avatarP = it
                    canteenDAO.updateProduct(product,product.key)
                })


            Log.i("TAG", "initView: "+product.key)
        }
    }

    override fun onResume() {
        super.onResume()
        edt_nameP.addTextChangedListener {
            product.nameP = it.toString()
        }
        editTextTextPersonName2.addTextChangedListener {
            if(it.toString() != ""){
                product.price = it.toString().toInt()
            }else product.price = 0
        }
        edt_detail.addTextChangedListener {
            product.detail = it.toString()
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                product.donVi = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                product.type = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddProductFrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddProductFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}