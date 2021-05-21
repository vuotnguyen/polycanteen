package com.example.canteenpoly.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.canteenpoly.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.fragment_home.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFrag : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,  // LifecycleOwner
            callback
        )
        checkPerMission()

    }

    private fun checkPerMission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            100
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(),"Bạn phải chấp nhận mới dùng được",Toast.LENGTH_SHORT).show()
            } else {
                ActivityCompat.finishAffinity(requireActivity())
            }
        }
    }

        @SuppressLint("CommitPrefEdits")
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val bundle = bundleOf("uid" to auth.currentUser.uid)
            uid = auth.currentUser.uid
            FirebaseMessaging.getInstance().token.addOnCompleteListener {
                if (!it.isSuccessful) {
                    Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_SHORT)
                        .show()
                } else {
//                sharePref = requireContext().getSharedPreferences("Token", Context.MODE_PRIVATE)
//                editor = sharePref.edit()
//                editor.putString("token", it.result.toString())
//                editor.apply()
                    token = it.result.toString()
                }
            }

            // Inflate the layout for this fragment
            var view = inflater.inflate(R.layout.fragment_home, container, false)
            view.img_chat.setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.action_homeFrag_to_chatFrag)
            }
            view.img_order.setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.action_homeFrag_to_orderStaticFrag)
            }
            view.img_product.setOnClickListener {
                Navigation.findNavController(view)
                    .navigate(R.id.action_homeFrag_to_productFrag, bundle)
            }
            view.img_profile.setOnClickListener {
                Navigation.findNavController(view)
                    .navigate(R.id.action_homeFrag_to_profileFrag, bundle)
            }
            return view
        }


        companion object {
            //        lateinit var editor: SharedPreferences.Editor
//        lateinit var sharePref: SharedPreferences
            var token = ""
            var uid = ""

            /**
             * Use this factory method to create a new instance of
             * this fragment using the provided parameters.
             *
             * @param param1 Parameter 1.
             * @param param2 Parameter 2.
             * @return A new instance of fragment HomeFrag.
             */

            @JvmStatic
            fun newInstance(param1: String, param2: String) =
                HomeFrag().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
        }


    }