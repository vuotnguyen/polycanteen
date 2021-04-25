package com.example.canteenpoly.fragment

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.canteenpoly.R
import com.example.canteenpoly.adapter.ListImgAdaper
import com.example.canteenpoly.callBack.BackListImg
import kotlinx.android.synthetic.main.fragment_list_img.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
@SuppressLint("StaticFieldLeak")
private lateinit var listImgAdaper: ListImgAdaper
private lateinit var listImg: ArrayList<String>
private lateinit var recyclerView: RecyclerView


/**
 * A simple [Fragment] subclass.
 * Use the [ListImgFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListImgFrag : Fragment(), BackListImg {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val view = inflater.inflate(R.layout.fragment_list_img, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        listImg = getAllImgFromDevice()
        recyclerView = view.rv_listImg
        listImgAdaper = ListImgAdaper(listImg, requireContext(), this)
        val manager = GridLayoutManager(requireContext(), 3, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = manager
        recyclerView.adapter = listImgAdaper
    }

    private fun getAllImgFromDevice(): ArrayList<String> {
        listImg = ArrayList()
        val collumn: Array<String> =
            Array(3) { MediaStore.Images.Media._ID; MediaStore.Images.Media.TITLE;MediaStore.Images.Media.DATA }
        val orderBy = MediaStore.Images.Media._ID
        val cursor: Cursor = requireActivity().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            collumn,
            null,
            null,
            orderBy
        )!!
        val count = cursor.count
        if(count >0){
            cursor.moveToFirst()
            while (!cursor.isAfterLast){
                val index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                val path = cursor.getString(index)
                listImg.add(path)
                cursor.moveToNext()
            }
        }
        return listImg
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListImgFrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListImgFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @SuppressLint("ResourceType")
    override fun sendPath(path: String) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set("key", path)
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

}